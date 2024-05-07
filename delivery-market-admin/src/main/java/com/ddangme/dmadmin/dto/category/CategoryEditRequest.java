package com.ddangme.dmadmin.dto.category;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@ToString
@Data
public class CategoryEditRequest {

    private String name;
    private List<Long> childIds;
    private List<String> childName;
    private List<String> newChildName;
    private List<Long> delCategoryIds;

//    public CategoryDTO toDTO(Long id) {
//        return new CategoryDTO(id, name, calChild(id));
//    }
//
//    private Set<CategoryDTO> calChild(Long parentId) {
//        if (childIds == null) {
//            return Set.of();
//        }
//
//        Set<CategoryDTO> childs = new LinkedHashSet<>();
//
//        for (int i = 0; i < childIds.size(); i++) {
//            childIds.get(i);
//            childs.add(new CategoryDTO(childIds.get(i), childName.get(i), parentId));
//        }
//
//        return childs;
//    }
//
//    public List<CategoryDTO> newChildToDTO(Long parentId) {
//        List<CategoryDTO> childs = new ArrayList<>();
//
//        if (newChildName != null) {
//            for (String name : newChildName) {
//                childs.add(new CategoryDTO(name, parentId));
//            }
//        }
//
//        return childs;
//    }


}
