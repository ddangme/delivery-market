package com.ddangme.dmadmin.repository.order;

import com.ddangme.dmadmin.dto.order.OrderResponse;
import com.ddangme.dmadmin.dto.order.QOrderResponse;
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
    public Page<OrderResponse> search(Pageable pageable) {
        List<OrderResponse> content = queryFactory
                .select(new QOrderResponse(
                        order.id.as("id"),
                        good.name.as("name"),
                        orderGood.price.as("price"),
                        order.deliveryStatus.as("deliveryStatus"),
                        member.name.as("member")
                ))
                .from(order)
                .innerJoin(orderGood).on(order.id.eq(orderGood.order.id))
                .innerJoin(good).on(good.id.eq(orderGood.good.id))
                .innerJoin(member).on(order.member.id.eq(member.id))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(order.count())
                .from(order);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }
}
