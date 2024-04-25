package com.ddangme.dmadmin.repository.goods;

import com.ddangme.dmadmin.model.goods.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);
}
