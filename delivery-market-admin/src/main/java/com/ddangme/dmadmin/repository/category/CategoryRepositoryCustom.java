package com.ddangme.dmadmin.repository.category;

import com.ddangme.dmadmin.model.good.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryRepositoryCustom {

    Page<Category> searchList(Pageable pageable);
}
