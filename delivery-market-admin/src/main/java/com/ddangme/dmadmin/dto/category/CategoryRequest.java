package com.ddangme.dmadmin.dto.category;

import com.ddangme.dmadmin.exception.DMAdminException;
import com.ddangme.dmadmin.exception.ErrorCode;
import com.ddangme.dmadmin.model.goods.Category;
import lombok.Data;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class CategoryRequest {

    private String name;
    private List<String> childName;

    public Category toEntity() {
        validateAndTrim();

        Category category = new Category(name);

        if (!childName.isEmpty()) {
            setChildCategories(category);
        }

        return category;
    }

    private void setChildCategories(Category category) {
        if (childName.stream().distinct().count() != childName.size()) {
            throw new DMAdminException(ErrorCode.DUPLICATE_CATEGORY_NAME);
        }

        Set<Category> childCategories = childName.stream()
                .map(name -> new Category(name))
                .collect(Collectors.toCollection(LinkedHashSet::new));

        category.addChildCategories(childCategories);
    }


    private void validateAndTrim() {
        if (name == null) {
            throw new DMAdminException(ErrorCode.BAD_REQUEST);
        }

        if (childName == null) {
            throw new DMAdminException(ErrorCode.BAD_REQUEST);
        }

        allNameTrim();
        validate(name);
        childName.forEach(this::validate);

        if (childName.stream().anyMatch(child -> child.equals(this.name))) {
            throw new DMAdminException(ErrorCode.DUPLICATE_PARENT_CATEGORY_NAME);
        }
    }

    private void allNameTrim() {
        this.name = name.trim();
        this.childName = childName.stream()
                .map(String::trim)
                .collect(Collectors.toList());
    }

    private void validate(String name) {
        if (name == null || name.isEmpty()) {
            throw new DMAdminException(ErrorCode.UNABLE_LENGTH_CATEGORY_NAME);
        }

        if (name.length() < 2 || name.length() > 15) {
            throw new DMAdminException(ErrorCode.UNABLE_LENGTH_CATEGORY_NAME);
        }
    }

}
