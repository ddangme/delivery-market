package com.ddangme.dmadmin.dto.goods;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class ParentCategoryResponse {

    private Long id;
    private String name;

    @QueryProjection
    public ParentCategoryResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

}
