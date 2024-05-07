package com.ddangme.dmadmin.service.goods;

import com.ddangme.dmadmin.dto.AdminDTO;
import com.ddangme.dmadmin.dto.category.CategoryDTO;
import com.ddangme.dmadmin.dto.category.CategoryIdNameResponse;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final AdminRepository adminRepository;
    private final CategoryRepository categoryRepository;


    @Transactional
    public void save(CategoryDTO dto) {
        checkDuplicate(dto);
        dto.getChildCategories().forEach(this::checkDuplicate);
        checkDuplicateOfChildName(dto.getChildCategories());

        categoryRepository.save(dto.toEntity());
    }

    private void checkDuplicate(CategoryDTO dto) {
        categoryRepository.findByName(dto.getName())
                .ifPresent(findCategory -> {throw new DMAdminException(ErrorCode.DUPLICATE_CATEGORY_NAME);});
    }

    private void checkDuplicateOfChildName(Set<CategoryDTO> childs) {
        List<String> names = new ArrayList<>();
        for (CategoryDTO child : childs) {
            names.add(child.getName());
        }

        if (names.stream().toList().contains("")) {
            throw new DMAdminException(ErrorCode.UNABLE_LENGTH_CATEGORY_NAME);
        }

        if (names.stream().distinct().count() != names.size()) {
            throw new DMAdminException(ErrorCode.DUPLICATE_CATEGORY_NAME);
        }
    }

    public Page<CategoryListResponse> searchList(Pageable pageable) {
        return categoryRepository.searchList(pageable)
                .map(CategoryListResponse::fromEntity);
    }

    public List<CategoryIdNameResponse> findParent() {
        return categoryRepository.findByParentIdIsNullOrderByName();
    }

    public CategoryDTO findByParentId(Long parentId) {
        return categoryRepository.findById(parentId)
                .map(CategoryDTO::fromEntity)
                .orElseThrow(() -> new DMAdminException(ErrorCode.NOT_EXIST_CATEGORY));
    }

    @Transactional
    public void edit(CategoryDTO dto) {
        Category parent = categoryRepository.findById(dto.getId())
                .orElseThrow(() -> new DMAdminException(ErrorCode.NOT_EXIST_CATEGORY));

        checkEditDuplicate(dto);
        parent.editName(dto.getName());

        saveChildCategory(dto.getChildCategories());
    }

    private void saveChildCategory(Set<CategoryDTO> dtos) {
        if (dtos.isEmpty()) {
            return;
        }

        for (CategoryDTO dto : dtos) {
            Category category = categoryRepository.findById(dto.getId())
                    .orElseThrow(() -> new DMAdminException(ErrorCode.NOT_EXIST_CATEGORY));
            checkEditDuplicate(dto);
            category.editName(dto.getName());
        }

    }

    @Transactional
    public void saveChildCategory(Set<CategoryDTO> dtos, Long parentId) {
        Category category = categoryRepository.findById(parentId)
                .orElseThrow(() -> new DMAdminException(ErrorCode.NOT_EXIST_PARENT_CATEGORY));

        if (!dtos.isEmpty()) {
            for (CategoryDTO dto : dtos) {
                checkDuplicate(dto);
                category.addChildCategories(List.of(dto.toEntity()));
            }
        }

    }

    private void checkEditDuplicate(CategoryDTO dto) {
        categoryRepository.findByName(dto.getName())
                .ifPresent(category -> {
                    if (!category.getId().equals(dto.getId())) {
                        throw new DMAdminException(ErrorCode.DUPLICATE_CATEGORY_NAME);
                    }
                });
    }



    @Transactional
    public void delete(List<Long> categoryIds, AdminDTO dto) {
        if (categoryIds == null || categoryIds.isEmpty()) {
            return;
        }

        Admin admin = findAdmin(dto);

        for (Long categoryId : categoryIds) {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new DMAdminException(ErrorCode.NOT_EXIST_CATEGORY));

            if (!category.getChildCategories().isEmpty()) {
                throw new DMAdminException(ErrorCode.EXIST_CHILD_CATEGORY);
            }

            category.delete(admin);
        }
    }

    private Admin findAdmin(AdminDTO dto) {
        return adminRepository.findById(dto.getId())
                .orElseThrow(() -> new DMAdminException(ErrorCode.ADMIN_NOT_FOUND));
    }

    public List<CategoryIdNameResponse> findChild(Long parentId) {
        return categoryRepository.findByParentIdOrderByName(parentId);
    }

}
