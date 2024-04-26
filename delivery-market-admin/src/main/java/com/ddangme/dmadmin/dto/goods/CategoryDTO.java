package com.ddangme.dmadmin.dto.goods;

import com.ddangme.dmadmin.dto.AdminDTO;
import com.ddangme.dmadmin.model.goods.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
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

    public Category toEntity() {
        return new Category(id, name, parentId);
    }

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


    public CategoryDTO(String name, Long parentId) {
        this.name = name;
        this.parentId = parentId;
    }

    public void trim() {
        name = name.trim();
    }
}
