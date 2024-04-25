package com.ddangme.dmadmin.dto.goods;

import lombok.Data;

@Data
public class CategoryRequest {

    private String name;
    private Long parentId;

    public CategoryDTO toDTO() {
        return new CategoryDTO(name, parentId);
    }

}
