package com.ddangme.dmadmin.repository.category;

import com.ddangme.dmadmin.dto.category.CategoryIdNameResponse;
import com.ddangme.dmadmin.model.good.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long>, CategoryRepositoryCustom {
    Optional<Category> findByName(String name);
    List<CategoryIdNameResponse> findByParentIdIsNullOrderByName();

    List<Category> findByParentIdIsNullOrderById();

    List<CategoryIdNameResponse> findByParentIdOrderByName(Long parentId);


}
