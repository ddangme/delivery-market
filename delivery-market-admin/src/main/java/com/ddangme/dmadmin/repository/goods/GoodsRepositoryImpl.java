package com.ddangme.dmadmin.repository.goods;

import com.ddangme.dmadmin.dto.goods.GoodsListResponse;
import com.ddangme.dmadmin.dto.goods.QGoodsListResponse;
import com.ddangme.dmadmin.model.QAdmin;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import javax.persistence.EntityManager;

import static com.ddangme.dmadmin.model.QAdmin.admin;
import static com.ddangme.dmadmin.model.goods.QCategory.category;
import static com.ddangme.dmadmin.model.goods.QGoods.goods;

public class GoodsRepositoryImpl implements GoodsRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public GoodsRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public Page<GoodsListResponse> search(Pageable pageable) {
        QAdmin admin1 = new QAdmin("admin1");
        QAdmin admin2 = new QAdmin("admin2");
        QAdmin admin3 = new QAdmin("admin3");
        JPAQuery<GoodsListResponse> query = queryFactory
                .select(new QGoodsListResponse(
                        goods.id.as("id"),
                        category.name.as("categoryName"),
                        goods.name,
                        goods.price,
                        goods.saleStatus,
                        goods.photo.uploadFileName.as("uploadFileName"),
                        goods.photo.storeFileName.as("storeFileName"),
                        goods.createdAt,
                        admin1.name.as("createdBy"),
                        goods.updatedAt,
                        admin2.name.as("updatedBy"), // 수정된 부분
                        goods.deletedAt,
                        admin3.name.as("deletedBy"))) // 수정된 부분
                .from(goods)
                .leftJoin(category).on(goods.category.id.eq(category.id))
                .leftJoin(admin1).on(goods.createdBy.id.eq(admin1.id)) // 수정된 부분
                .leftJoin(admin2).on(goods.updatedBy.id.eq(admin2.id)) // 수정된 부분
                .leftJoin(admin3).on(goods.deletedBy.id.eq(admin3.id)); // 수정된 부분

        return PageableExecutionUtils.getPage(query.fetch(), pageable, query::fetchCount);
    }

}
