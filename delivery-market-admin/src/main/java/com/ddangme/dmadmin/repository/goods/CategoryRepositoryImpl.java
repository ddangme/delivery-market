package com.ddangme.dmadmin.repository.goods;

import com.ddangme.dmadmin.model.goods.Category;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;

import static com.ddangme.dmadmin.model.goods.QCategory.category;

public class CategoryRepositoryImpl implements CategoryRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public CategoryRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<Category> searchParents() {
        return queryFactory
                .selectFrom(category)
                .where(category.parentId.isNull()
                        .and(category.deletedAt.isNull()))
                .fetch();
    }
}
