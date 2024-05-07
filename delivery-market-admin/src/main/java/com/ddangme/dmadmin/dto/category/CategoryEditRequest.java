package com.ddangme.dmadmin.dto.category;

import com.ddangme.dmadmin.exception.DMAdminException;
import com.ddangme.dmadmin.exception.ErrorCode;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ToString
@Data
public class CategoryEditRequest {

    private String name;
    private List<CategoryIdName> oldChild = new ArrayList<>();
    private List<String> newChildName = new ArrayList<>();
    private List<Long> delCategoryIds = new ArrayList<>();

    public CategoryDTO toDTO(Long id) {
        nullCheck(name);
        CategoryDTO dto = new CategoryDTO(id, name);
        Set<CategoryDTO> childDto = new LinkedHashSet<>();

        oldChild.stream().filter(child -> child.id != null)
                .forEach(child -> childDto.add(new CategoryDTO(child.id, child.name)));

        dto.setChildCategories(childDto);
        return dto;
    }

    public Set<CategoryDTO> toNewDTO() {
        return newChildName.stream()
                .peek(this::nullCheck)
                .map(CategoryDTO::new)
                .collect(Collectors.toCollection(LinkedHashSet::new));

    }

    private void nullCheck(String name) {
        if (name == null) {
            throw new DMAdminException(ErrorCode.BAD_REQUEST);
        }
    }

    @Data
    static class CategoryIdName {
        private Long id;
        private String name;
    }


}
