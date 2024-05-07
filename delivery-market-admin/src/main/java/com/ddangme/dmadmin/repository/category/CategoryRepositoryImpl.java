package com.ddangme.dmadmin.repository.category;

import com.ddangme.dmadmin.model.goods.Category;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import javax.persistence.EntityManager;
import java.util.List;

import static com.ddangme.dmadmin.model.goods.QCategory.category;

public class CategoryRepositoryImpl implements CategoryRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public CategoryRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public Page<Category> searchList(Pageable pageable) {
        List<Category> content = queryFactory
                .selectFrom(category)
                .where(category.parent.isNull())
                .orderBy(category.name.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(category.count())
                .from(category)
                .where(category.parent.isNull(),
                        category.deletedAt.isNotNull());

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }
}
