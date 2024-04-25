package com.ddangme.dmadmin.dto.goods;

import com.ddangme.dmadmin.model.goods.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CategoryDTO {
    
    private Long id;
    private String name;
    private Long parentId;

    private LocalDateTime createdAt;

    private Long createdBy;

    private LocalDateTime updatedAt;
    private Long updatedBy;
    private LocalDateTime deletedAt;

    private Long deletedBy;

    public Category toEntity() {
        return new Category(id, name, parentId);
    }

    public CategoryDTO(String name, Long parentId) {
        this.name = name;
        this.parentId = parentId;
    }

    public void trim() {
        name = name.trim();
        System.out.println(name);
    }
}
