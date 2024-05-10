package com.ddangme.dm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AdminURL {
    BASIC("http://localhost:8081"),
    GET_CATEGORIES_FROM_CHILD_ID(BASIC.url + "/api/categories/parents/"),
    GET_ALL_CATEGORIES(BASIC.url + "/api/categories"),
    GET_ALL_GOODS(BASIC.url + "/api/goods"),
    GET_GOODS_IN_CATEGORY(BASIC.url + "/api/goods/"),
    ;



    private final String url;

}
