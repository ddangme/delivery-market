package com.ddangme.dmadmin.dto.category;

import com.ddangme.dmadmin.model.good.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class CategoryResponse {
    private Long parentId;
    private String parentName;
    private Long childId;
    private String childName;

    public CategoryResponse(Long parentId, String parentName) {
        this.parentId = parentId;
        this.parentName = parentName;
    }

    public static CategoryResponse fromEntity(Category entity) {
        if (entity.getParent() != null) {
            return new CategoryResponse(
                    entity.getParent().getId(),
                    entity.getParent().getName(),
                    entity.getId(),
                    entity.getName()
            );
        }

        return new CategoryResponse(
                entity.getId(),
                entity.getName()
        );
    }
}
