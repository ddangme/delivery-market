package com.ddangme.dmadmin.repository.good;


import com.ddangme.dmadmin.dto.good.response.*;
import com.ddangme.dmadmin.model.admin.QAdmin;
import com.ddangme.dmadmin.model.constants.SaleStatus;
import com.ddangme.dmadmin.model.good.Good;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.ddangme.dmadmin.model.good.QCategory.category;
import static com.ddangme.dmadmin.model.good.QGood.good;

public class GoodRepositoryImpl implements GoodRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public GoodRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public Page<GoodListResponse> search(Pageable pageable) {
        QAdmin admin1 = new QAdmin("admin1");
        QAdmin admin2 = new QAdmin("admin2");
        JPAQuery<GoodListResponse> query = queryFactory
                .select(new QGoodListResponse(
                        good.id.as("id"),
                        category.name.as("categoryName"),
                        good.name,
                        good.price,
                        good.saleStatus,
                        good.photo.uploadFileName.as("uploadFileName"),
                        good.photo.storeFileName.as("storeFileName"),
                        good.createdAt,
                        admin1.name.as("createdBy"),
                        good.updatedAt,
                        admin2.name.as("updatedBy")))
                .from(good)
                .leftJoin(category).on(good.category.id.eq(category.id))
                .leftJoin(admin1).on(good.createdBy.id.eq(admin1.id))
                .leftJoin(admin2).on(good.updatedBy.id.eq(admin2.id))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                ;

        return PageableExecutionUtils.getPage(query.fetch(), pageable, query::fetchCount);
    }

    @Override
    public Page<GoodResponse> searchForMember(Pageable pageable) {
        List<GoodResponse> content = queryFactory
                .select(new QGoodResponse(
                        good.id,
                        good.name,
                        good.summary,
                        good.price,
                        good.discountPrice,
                        good.discountPercent,
                        good.saleStatus,
                        good.photo.storeFileName
                ))
                .from(good)
                .where(good.deletedAt.isNull())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(good.count())
                .from(good)
                .where(good.deletedAt.isNull());

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    @Override
    public Page<GoodSaleResponse> searchGoodsInCategoryId(Pageable pageable, Long categoryId) {
        List<GoodSaleResponse> content = queryFactory
                .select(new QGoodSaleResponse(
                        good.id,
                        good.name,
                        good.summary,
                        good.price,
                        good.discountPrice,
                        good.discountPercent,
                        good.saleStatus,
                        good.photo.storeFileName
                ))
                .from(good)
                .where(good.deletedAt.isNull()
                        .and(good.category.id.eq(categoryId)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(good.count())
                .from(good)
                .where(good.deletedAt.isNull()
                        .and(good.category.id.eq(categoryId)));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    @Override
    public Optional<Good> searchSaleGoodByGoodId(Long goodId) {
        Good result = queryFactory
                .selectFrom(good)
                .where(good.id.eq(goodId)
                        .and(good.deletedAt.isNull())
                        .and(good.saleStatus.notIn(SaleStatus.END)))
                .fetchOne();

        return Optional.ofNullable(result);

    }
}
