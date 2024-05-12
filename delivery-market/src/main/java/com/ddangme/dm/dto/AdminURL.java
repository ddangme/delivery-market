package com.ddangme.dm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AdminURL {
    BASIC("http://localhost:8081/api"),
    CATEGORIES_FROM_CHILD_ID(BASIC.url + "/categories/parents/"),
    ALL_CATEGORIES(BASIC.url + "/categories"),
    ALL_GOODS(BASIC.url + "/goods"),
    GOODS_IN_CATEGORY(BASIC.url + "/goods/"),
    GOOD_DETAIL(BASIC.url + "/goods/detail/"),
    ;



    private final String url;

}
