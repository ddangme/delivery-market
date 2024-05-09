package com.ddangme.dmadmin.dto.category;

import com.ddangme.dmadmin.model.good.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

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
    private List<CategoryListResponse> childCategories;

    public static CategoryListResponse fromEntity(Category entity) {
        return new CategoryListResponse(
                entity.getId(),
                entity.getName(),
                entity.getCreatedAt(),
                entity.getCreatedBy().getName(),
                entity.getUpdatedAt(),
                entity.getUpdatedBy().getName(),
                entity.getChildCategories().stream()
                        .map(CategoryListResponse::fromEntity)
                        .sorted(Comparator.comparing(CategoryListResponse::getId))
                        .toList()
        );
    }

}
