package com.ddangme.dm.repository.cart;

import com.ddangme.dm.dto.address.CartValidateProjection;
import com.ddangme.dm.dto.address.QCartValidateProjection;
import com.ddangme.dm.dto.cart.CartChangeCountProjection;
import com.ddangme.dm.dto.cart.CartListProjection;
import com.ddangme.dm.dto.cart.QCartChangeCountProjection;
import com.ddangme.dm.dto.cart.QCartListProjection;
import com.ddangme.dm.dto.order.OrderCartProjection;
import com.ddangme.dm.dto.order.QOrderCartProjection;
import com.ddangme.dm.model.constants.SaleStatus;
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
    public List<CartListProjection> findByMemberId(Long memberId) {
        return queryFactory
                .select(new QCartListProjection(
                        cart.id.as("id"),
                        good.id.as("goodId"),
                        cart.option.id.as("optionId"),
                        goodOption.name.as("optionName"),
                        good.name.as("goodName"),
                        cart.quantity.as("quantity"),
                        goodOption.quantity.as("remainQuantity"),
                        good.photoStoreFileName.as("photo"),
                        goodDetail.packagingType.as("packagingType"),
                        goodOption.saleStatus.as("saleStatus"),
                        cart.status.as("checkStatus"),
                        goodOption.price.as("price"),
                        goodOption.discountPrice.as("discountPrice")))
                .from(cart)
                .innerJoin(goodOption).on(cart.option.id.eq(goodOption.id))
                .innerJoin(good).on(goodOption.good.id.eq(good.id))
                .innerJoin(goodDetail).on(good.id.eq(goodDetail.good.id))
                .where(cart.member.id.eq(memberId))
                .fetch();
    }

    @Override
    public CartChangeCountProjection findByOptionPriceInCart(Long cartId) {
        return queryFactory
                .select(new QCartChangeCountProjection(
                        cart.id.as("id"),
                        goodOption.price.as("price"),
                        goodOption.discountPrice.as("discountPrice")))
                .from(cart)
                .innerJoin(goodOption).on(cart.option.id.eq(goodOption.id))
                .where(cart.id.eq(cartId))
                .fetchOne();
    }

    @Override
    public List<OrderCartProjection> findByMemberIdAtOrder(Long memberId) {
        return queryFactory
                .select(new QOrderCartProjection(
                        cart.id,
                        good.photoStoreFileName.as("photo"),
                        goodOption.name.as("optionName"),
                        good.name.as("goodName"),
                        cart.quantity.as("optionCount"),
                        goodOption.price.as("price"),
                        goodOption.discountPrice.as("discountPrice")
                ))
                .from(cart)
                .innerJoin(goodOption).on(cart.option.id.eq(goodOption.id))
                .innerJoin(good).on(goodOption.good.id.eq(good.id))
                .innerJoin(goodDetail).on(good.id.eq(goodDetail.good.id))
                .where(cart.member.id.eq(memberId)
                        .and(cart.status.isTrue())
                        .and(goodOption.quantity.eq(0).not())
                        .and(
                                (goodOption.saleStatus.eq(SaleStatus.AVAILABLE))
                                        .or(goodOption.saleStatus.eq(SaleStatus.ON_SALE))))
                .fetch();

    }

    @Override
    public List<CartValidateProjection> findForValidateByMemberId(Long memberId) {
        return queryFactory
                .select(new QCartValidateProjection(
                        cart.id,
                        cart.quantity.as("buyQuantity"),
                        goodOption.quantity.as("remainQuantity"),
                        goodOption.price.as("price"),
                        goodOption.discountPrice.as("discountPrice")
                ))
                .from(cart)
                .innerJoin(goodOption).on(cart.option.id.eq(goodOption.id))
                .where(cart.member.id.eq(memberId)
                        .and(cart.status.isTrue())
                        .and(goodOption.quantity.eq(0).not())
                        .and(
                                (goodOption.saleStatus.eq(SaleStatus.AVAILABLE))
                                        .or(goodOption.saleStatus.eq(SaleStatus.ON_SALE))))
                .fetch();
    }

}
