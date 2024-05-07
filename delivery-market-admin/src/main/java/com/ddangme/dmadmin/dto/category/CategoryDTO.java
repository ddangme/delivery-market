package com.ddangme.dmadmin.dto.category;

import com.ddangme.dmadmin.dto.AdminDTO;
import com.ddangme.dmadmin.exception.DMAdminException;
import com.ddangme.dmadmin.exception.ErrorCode;
import com.ddangme.dmadmin.model.goods.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class CategoryDTO {

    private Long id;
    private String name;
    private CategoryDTO parent;

    private LocalDateTime createdAt;
    private AdminDTO createdBy;
    private LocalDateTime updatedAt;
    private AdminDTO updatedBy;
    private LocalDateTime deletedAt;
    private AdminDTO deletedBy;

    private Set<CategoryDTO> childCategories = new LinkedHashSet<>();

    public CategoryDTO(String name) {
        this.name = name.trim();
    }

    public Category toEntity() {
        checkNameLength(name);
        childCategories.forEach(child -> checkNameLength(child.name));

        if (childCategories.stream().anyMatch(child -> child.name.equals(this.name))) {
            throw new DMAdminException(ErrorCode.DUPLICATE_PARENT_CATEGORY_NAME);
        }

        Category category = new Category(name);
        category.addChildCategories(childCategories.stream().map(CategoryDTO::toEntity)
                .collect(Collectors.toUnmodifiableSet()));

        return category;
    }

    private void checkNameLength(String name) {
        if (name.isEmpty()) {
            throw new DMAdminException(ErrorCode.UNABLE_LENGTH_CATEGORY_NAME);
        }
        if (name.length() < 2 || name.length() > 15) {
            throw new DMAdminException(ErrorCode.UNABLE_LENGTH_CATEGORY_NAME);
        }
    }
}
