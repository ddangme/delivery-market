package com.ddangme.dmadmin.repository.goods;

import com.ddangme.dmadmin.dto.goods.ParentCategoryResponse;
import com.ddangme.dmadmin.model.goods.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryRepositoryCustom {

    Page<Category> searchParents(Pageable pageable);

    List<ParentCategoryResponse> searchParents();
}
