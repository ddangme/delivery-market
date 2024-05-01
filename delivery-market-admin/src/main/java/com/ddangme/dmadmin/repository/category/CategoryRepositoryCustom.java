package com.ddangme.dmadmin.repository.category;

import com.ddangme.dmadmin.model.goods.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryRepositoryCustom {

    Page<Category> searchParents(Pageable pageable);

}
