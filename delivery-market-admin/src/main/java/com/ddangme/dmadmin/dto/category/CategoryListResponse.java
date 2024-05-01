package com.ddangme.dmadmin.dto.category;

import com.ddangme.dmadmin.model.goods.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@ToString
@Getter
@AllArgsConstructor
public class CategoryListResponse {

    private Long id;
    private String name;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
    private Set<CategoryListResponse> childCategories;

    public static CategoryListResponse fromEntity(Category entity) {

        return new CategoryListResponse(
                entity.getId(),
                entity.getName(),
                entity.getCreatedAt(),
                entity.getCreatedBy().getName(),
                entity.getUpdatedAt(),
                entity.getUpdatedBy().getName(),
                entity.getChildCategories().stream().map(CategoryListResponse::fromEntity).collect(Collectors.toUnmodifiableSet())
        );
    }

    public CategoryListResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

}
