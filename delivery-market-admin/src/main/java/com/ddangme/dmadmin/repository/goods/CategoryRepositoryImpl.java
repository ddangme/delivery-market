package com.ddangme.dmadmin.repository.goods;

import com.ddangme.dmadmin.model.goods.Category;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import javax.persistence.EntityManager;

import static com.ddangme.dmadmin.model.goods.QCategory.category;

public class CategoryRepositoryImpl implements CategoryRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public CategoryRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public Page<Category> searchParents(Pageable pageable) {
        JPAQuery<Category> query = queryFactory
                .selectFrom(category)
                .from(category)
                .where(category.parentId.isNull()
                        .and(category.deletedAt.isNull()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        return PageableExecutionUtils.getPage(query.fetch(), pageable,
                query::fetchCount);
    }
}
