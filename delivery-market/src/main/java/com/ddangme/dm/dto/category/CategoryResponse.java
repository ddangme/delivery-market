package com.ddangme.dm.dto.category;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class CategoryResponse {

    private Long id;
    private String name;
    private List<CategoryResponse> childs = new ArrayList<>();

}
