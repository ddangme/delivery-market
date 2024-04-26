package com.ddangme.dmadmin.service.goods;

import com.ddangme.dmadmin.dto.AdminDTO;
import com.ddangme.dmadmin.dto.goods.CategoryDTO;
import com.ddangme.dmadmin.exception.DMAdminException;
import com.ddangme.dmadmin.exception.ErrorCode;
import com.ddangme.dmadmin.model.Admin;
import com.ddangme.dmadmin.model.goods.Category;
import com.ddangme.dmadmin.repository.AdminRepository;
import com.ddangme.dmadmin.repository.goods.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final AdminRepository adminRepository;
    private final CategoryRepository categoryRepository;

    public Page<CategoryDTO> search(Pageable pageable) {
        return categoryRepository.searchParents(pageable)
                .map(CategoryDTO::fromEntity);
    }

    @Transactional
    public void save(CategoryDTO dto) {
        saveValidate(dto);
        Category category = dto.toEntity();

        categoryRepository.save(category);
    }

    @Transactional
    public void delete(List<Long> categoryIds, AdminDTO dto) {
        if (categoryIds.isEmpty()) {
            throw new DMAdminException(ErrorCode.NOT_CHOICE_CATEGORY);
        }

        Admin admin = adminRepository.findById(dto.getId())
                .orElseThrow(() -> new DMAdminException(ErrorCode.ADMIN_NOT_FOUND));

        for (Long categoryId : categoryIds) {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new DMAdminException(ErrorCode.NOT_EXIST_CATEGORY));

            if (!category.getChildCategories().isEmpty()) {
                throw new DMAdminException(ErrorCode.EXIST_CHILD_CATEGORY);
            }

            category.delete(admin);
        }
    }

    private void saveValidate(CategoryDTO dto) {
        if (dto.getName() == null || dto.getName().isEmpty()) {
            throw new DMAdminException(ErrorCode.UNABLE_LENGTH_CATEGORY_NAME);
        }
        dto.trim();

        if (dto.getName().length() < 2 || dto.getName().length() > 15) {
            throw new DMAdminException(ErrorCode.UNABLE_LENGTH_CATEGORY_NAME);
        }

        if (dto.getParentId() != null) {
            categoryRepository.findById(dto.getParentId())
                    .orElseThrow(() -> new DMAdminException(ErrorCode.NOT_EXIST_PARENT_CATEGORY));
        }

        categoryRepository.findByName(dto.getName())
                .ifPresent(existCategory -> {
                    throw new DMAdminException(ErrorCode.DUPLICATE_CATEGORY_NAME);
                });
    }



}
