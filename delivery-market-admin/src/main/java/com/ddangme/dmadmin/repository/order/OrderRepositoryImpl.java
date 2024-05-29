package com.ddangme.dmadmin.repository.order;

import com.ddangme.dmadmin.dto.order.OrderResponse;
import com.ddangme.dmadmin.dto.order.QOrderResponse;
import com.ddangme.dmadmin.model.constants.DeliveryStatus;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import javax.persistence.EntityManager;

import java.util.List;

import static com.ddangme.dmadmin.model.good.QGood.good;
import static com.ddangme.dmadmin.model.member.QMember.member;
import static com.ddangme.dmadmin.model.order.QOrder.order;
import static com.ddangme.dmadmin.model.order.QOrderGood.orderGood;

public class OrderRepositoryImpl implements OrderRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public OrderRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }


    @Override
    public Page<OrderResponse> search(Pageable pageable, DeliveryStatus status, String keyword) {
        BooleanBuilder condition = new BooleanBuilder();
        if (status != null) {
            condition.and(order.deliveryStatus.eq(status));
        }
        if (keyword != null) {
            try {
                condition.and(order.id.eq(Long.parseLong(keyword))
                        .or(order.member.name.eq(keyword)));
            } catch (Exception e) {
                condition.and(order.member.name.eq(keyword));
            }
        }
        List<OrderResponse> content = queryFactory
                .select(new QOrderResponse(
                        order.id.as("id"),
                        order.totalDiscountPrice.as("price"),
                        order.deliveryStatus.as("deliveryStatus"),
                        member.name.as("member")
                ))
                .from(order)
                .leftJoin(member).on(order.member.id.eq(member.id))
                .where(condition)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(order.count())
                .from(order)
                .where(condition);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }
}
