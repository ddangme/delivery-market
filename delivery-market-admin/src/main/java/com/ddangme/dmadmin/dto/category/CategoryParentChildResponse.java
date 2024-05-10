package com.ddangme.dmadmin.dto.category;

import com.ddangme.dmadmin.model.good.Category;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class CategoryParentChildResponse {

    private Long id;
    private String name;
    private List<CategoryParentChildResponse> childs = new ArrayList<>();

    public static CategoryParentChildResponse fromEntity(Category category) {
        return new CategoryParentChildResponse(
                category.getId(),
                category.getName(),
                category.getChildCategories()
                        .stream().map(CategoryParentChildResponse::fromEntity)
                        .toList());
    }

}
