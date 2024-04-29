package com.ddangme.dmadmin.dto.goods;

import com.ddangme.dmadmin.exception.DMAdminException;
import com.ddangme.dmadmin.exception.ErrorCode;
import lombok.Data;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Data
public class CategoryRequest {

    private String name;
    private List<String> childName;

    public CategoryDTO toDTO() {
        if (childName != null) {
            Set<CategoryDTO> childCategories = new LinkedHashSet<>();

            for (String child : childName) {
                childCategories.add(new CategoryDTO(child));
            }

            if (!childName.isEmpty() && childName.size() != childCategories.size()) {
                throw new DMAdminException(ErrorCode.DUPLICATE_CATEGORY_NAME);
            }

            return new CategoryDTO(name, childCategories);
        }

        return new CategoryDTO(name);
    }


}
