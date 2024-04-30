package com.ddangme.dmadmin.dto.goods;

import com.ddangme.dmadmin.dto.AdminDTO;
import com.ddangme.dmadmin.model.goods.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class CategoryDTO {

    private Long id;
    private String name;
    private Long parentId;

    private LocalDateTime createdAt;
    private AdminDTO createdBy;
    private LocalDateTime updatedAt;
    private AdminDTO updatedBy;
    private LocalDateTime deletedAt;
    private AdminDTO deletedBy;

    private Set<CategoryDTO> childCategories = new LinkedHashSet<>();

    public CategoryDTO(Long id, String name, Long parentId, LocalDateTime createdAt, AdminDTO createdBy, LocalDateTime updatedAt, AdminDTO updatedBy, Set<CategoryDTO> childCategories) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
        this.childCategories = childCategories;
    }

    public static CategoryDTO fromEntity(Category entity) {
        return new CategoryDTO(
                entity.getId(),
                entity.getName(),
                entity.getParentId(),
                entity.getCreatedAt(),
                AdminDTO.fromEntity(entity.getCreatedBy()),
                entity.getUpdatedAt(),
                AdminDTO.fromEntity(entity.getUpdatedBy()),
                entity.getChildCategories().stream().map(CategoryDTO::fromEntity).collect(Collectors.toUnmodifiableSet())
        );
    }

    public Category toEntity() {
        return new Category(name, parentId);
    }

    public Set<Category> childToEntity(Long parentId) {
        Set<Category> categories = new LinkedHashSet<>();

        for (CategoryDTO childCategory : childCategories) {
            categories.add(new Category(childCategory.getName(), parentId));
        }

        return categories;
    }


    public CategoryDTO(String name) {
        this.name = name;
    }

    public CategoryDTO(String name, Set<CategoryDTO> childCategories) {
        this.name = name;
        this.childCategories = childCategories;
    }

    public CategoryDTO(Long id, String name, Set<CategoryDTO> childCategories) {
        this.id = id;
        this.name = name;
        this.childCategories = childCategories;
    }

    public CategoryDTO(Long id, String name, Long parentId) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
    }

    public CategoryDTO(String name, Long parentId) {
        this.name = name;
        this.parentId = parentId;
    }

    public void nameTrim() {
        this.name = name.trim();
    }
}
