package com.ddangme.dm.dto.category;

import com.ddangme.dm.model.category.Category;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Data
public class CategoryResponse {

    private Long id;
    private String name;
    private List<CategoryResponse> childs = new ArrayList<>();

    public static CategoryResponse fromEntity(Category entity) {
        return new CategoryResponse(
                entity.getId(),
                entity.getName(),
                entity.getChildCategories()
                        .stream().map(CategoryResponse::fromEntity)
                        .collect(Collectors.toList())
        );
    }
}
