package com.ddangme.dmadmin.dto.category;

import com.ddangme.dmadmin.dto.AdminDTO;
import com.ddangme.dmadmin.model.goods.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.*;

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
        this.name = name;
    }


}
