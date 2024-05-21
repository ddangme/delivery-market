package com.ddangme.dm.repository.member;

import com.ddangme.dm.dto.order.OrderAddressProjection;
import com.ddangme.dm.dto.order.QOrderAddressProjection;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;

import java.util.List;

import static com.ddangme.dm.model.QAddress.address;
import static com.ddangme.dm.model.member.QMember.member;

public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public MemberRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public OrderAddressProjection findMainAddressByMemberId(Long memberId) {
        return queryFactory
                .select(new QOrderAddressProjection(
                        address.id,
                        address.recipientName.as("name"),
                        address.recipientPhone.as("phone"),
                        address.road,
                        address.detail
                ))
                .from(address)
                .where(address.member.id.eq(memberId)
                        .and(address.main.isTrue()))
                .fetchOne();
    }

    @Override
    public List<OrderAddressProjection> findAddressListByMemberId(Long memberId) {
        return queryFactory
                .select(new QOrderAddressProjection(
                        address.id.as("id"),
                        address.recipientName.as("name"),
                        address.recipientPhone.as("phone"),
                        address.road.as("road"),
                        address.detail.as("detail"),
                        address.main.as("main")
                ))
                .from(address)
                .where(address.member.id.eq(memberId))
                .fetch();
    }

}
