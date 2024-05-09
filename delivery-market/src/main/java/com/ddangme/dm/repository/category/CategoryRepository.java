package com.ddangme.dm.repository.category;

import com.ddangme.dm.model.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findIdAndNameByParentIdIsNullOrderByName();
}
