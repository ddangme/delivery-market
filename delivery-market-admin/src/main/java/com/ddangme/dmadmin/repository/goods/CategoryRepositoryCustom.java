package com.ddangme.dmadmin.repository.goods;

import com.ddangme.dmadmin.model.goods.Category;

import java.util.List;

public interface CategoryRepositoryCustom {

    List<Category> searchParents();
}
