package com.ddangme.dmadmin.service.goods;

import com.ddangme.dmadmin.dto.AdminDTO;
import com.ddangme.dmadmin.dto.category.CategoryDTO;
import com.ddangme.dmadmin.dto.category.CategoryListResponse;
import com.ddangme.dmadmin.exception.DMAdminException;
import com.ddangme.dmadmin.exception.ErrorCode;
import com.ddangme.dmadmin.model.Admin;
import com.ddangme.dmadmin.model.goods.Category;
import com.ddangme.dmadmin.repository.AdminRepository;
import com.ddangme.dmadmin.repository.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final AdminRepository adminRepository;
    private final CategoryRepository categoryRepository;

    public Page<CategoryListResponse> search(Pageable pageable) {
        return categoryRepository.searchParents(pageable)
                .map(CategoryListResponse::fromEntity);
    }

    public CategoryDTO getParentCategory(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .map(CategoryDTO::fromEntity)
                .orElseThrow(() -> new DMAdminException(ErrorCode.NOT_EXIST_CATEGORY));
    }

    @Transactional
    public void save(CategoryDTO dto) {
        saveValidate(dto);

        Category parent = categoryRepository.save(dto.toEntity());

        if (!dto.getChildCategories().isEmpty()) {
            Set<Category> childCategories = dto.childToEntity(parent.getId());
            parent.addChildCategories(childCategories);
        }
    }

    @Transactional
    public void saveChild(List<CategoryDTO> dtos, Long parentId) {
        Category category = categoryRepository.findById(parentId)
                .orElseThrow(() -> new DMAdminException(ErrorCode.NOT_EXIST_PARENT_CATEGORY));

        if (!dtos.isEmpty()) {
            for (CategoryDTO dto : dtos) {
                saveValidate(dto);
                category.addChildCategories(List.of(dto.toEntity()));
            }
        }
    }

    @Transactional
    public void edit(CategoryDTO dto) {
        Category category = categoryRepository.findById(dto.getId())
                .orElseThrow(() -> new DMAdminException(ErrorCode.NOT_EXIST_CATEGORY));
        editValidate(dto);
        category.edit(dto.getName());

        for (CategoryDTO childCategoryDTO : dto.getChildCategories()) {
            editValidate(childCategoryDTO);

            Category childCategory = categoryRepository.findById(childCategoryDTO.getId())
                    .orElseThrow(() -> new DMAdminException(ErrorCode.NOT_EXIST_CATEGORY));

            childCategory.edit(childCategoryDTO.getName());
        }
    }

    private void editValidate(CategoryDTO dto) {
        if (dto.getName() == null || dto.getName().isEmpty()) {
            throw new DMAdminException(ErrorCode.UNABLE_LENGTH_CATEGORY_NAME);
        }

        dto.nameTrim();

        if (dto.getName().length() < 2 || dto.getName().length() > 15) {
            throw new DMAdminException(ErrorCode.UNABLE_LENGTH_CATEGORY_NAME);
        }
        categoryRepository.findByName(dto.getName())
                .ifPresent(category -> {
                    if (!category.getId().equals(dto.getId())) {
                        throw new DMAdminException(ErrorCode.DUPLICATE_CATEGORY_NAME);
                    }
                });

    }

    @Transactional
    public void delete(List<Long> categoryIds, AdminDTO dto) {
        if (categoryIds == null) {
            return;
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
        if (dto.getChildCategories() != null) {
            for (CategoryDTO childCategory : dto.getChildCategories()) {
                nameValidate(childCategory.getName());

                if (dto.getName().equals(childCategory.getName())) {
                    throw new DMAdminException(ErrorCode.DUPLICATE_PARENT_CATEGORY_NAME);
                }
            }
        }

        nameValidate(dto.getName());
    }

    private void nameValidate(String name) {
        if (name == null || name.isEmpty()) {
            throw new DMAdminException(ErrorCode.UNABLE_LENGTH_CATEGORY_NAME);
        }

        name = name.trim();

        if (name.length() < 2 || name.length() > 15) {
            throw new DMAdminException(ErrorCode.UNABLE_LENGTH_CATEGORY_NAME);
        }

        categoryRepository.findByName(name)
                .ifPresent(category -> {throw new DMAdminException(ErrorCode.DUPLICATE_CATEGORY_NAME);});
    }



}
