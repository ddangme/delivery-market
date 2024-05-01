package com.ddangme.dmadmin.repository.category;

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
                .where(category.parentId.isNull())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(category.name.asc());

        return PageableExecutionUtils.getPage(query.fetch(), pageable,
                query::fetchCount);
    }

//    @Override
//    public Page<CategoryListResponse> searchList(Pageable pageable) {
//        QAdmin a1 = new QAdmin("a1");
//        QAdmin a2 = new QAdmin("a2");
//
//        JPAQuery<CategoryListResponse> query = queryFactory
//                .select(new QCategoryListResponse(category.id.as("id"),
//                        category.name,
//                        category.createdAt,
//                        a1.name,
//                        category.updatedAt,
//                        a2.name))
//                .from(category)
//                .leftJoin(a1).on(category.createdBy.id.eq(a1.id))
//                .leftJoin(a2).on(category.updatedBy.id.eq(a2.id))
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .orderBy(category.id.desc());
//
//        return PageableExecutionUtils.getPage(query.fetch(), pageable,
//                query::fetchCount);
////        return null;
//    }
//

}
