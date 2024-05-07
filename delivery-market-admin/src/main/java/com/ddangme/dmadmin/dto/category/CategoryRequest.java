package com.ddangme.dmadmin.dto.category;

import com.ddangme.dmadmin.exception.DMAdminException;
import com.ddangme.dmadmin.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest {

    private String name;
    private List<String> childName;

    public CategoryDTO toDTO() {
        if (name == null) {
            throw new DMAdminException(ErrorCode.BAD_REQUEST);
        }

        if (childName == null) {
            throw new DMAdminException(ErrorCode.BAD_REQUEST);
        }

        CategoryDTO categoryDTO = new CategoryDTO(name);

        if (!childName.isEmpty()) {
            Set<CategoryDTO> childDTOs = childName.stream()
                    .map(CategoryDTO::new)
                    .collect(Collectors.toCollection(LinkedHashSet::new));

            categoryDTO.setChildCategories(childDTOs);
        }

        return categoryDTO;
    }

}
