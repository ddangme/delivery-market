package com.ddangme.dm.repository.cart;

import com.ddangme.dm.dto.good.CartProjection;
import com.ddangme.dm.dto.good.QCartProjection;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static com.ddangme.dm.model.good.QCart.cart;
import static com.ddangme.dm.model.good.QGood.good;
import static com.ddangme.dm.model.good.QGoodDetail.goodDetail;
import static com.ddangme.dm.model.good.QGoodOption.goodOption;

public class CartRepositoryImpl implements CartRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public CartRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<CartProjection> findByMemberId(Long memberId) {
        return queryFactory
                .select(new QCartProjection(
                        cart.id.as("id"),
                        cart.option.id.as("optionId"),
                        goodOption.name.as("optionName"),
                        good.name.as("goodName"),
                        cart.count,
                        good.photoStoreFileName.as("photo"),
                        goodDetail.packagingType.as("packagingType"),
                        goodOption.saleStatus.as("saleStatus"),
                        cart.status.as("checkStatus")))
                .from(cart)
                .innerJoin(goodOption).on(cart.option.id.eq(goodOption.id))
                .innerJoin(good).on(goodOption.good.id.eq(good.id))
                .innerJoin(goodDetail).on(good.id.eq(goodDetail.good.id))
                .where(cart.member.id.eq(memberId))
                .fetch();
    }
}
