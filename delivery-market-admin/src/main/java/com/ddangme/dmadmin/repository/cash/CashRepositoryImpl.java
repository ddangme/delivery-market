package com.ddangme.dmadmin.repository.cash;

import com.ddangme.dmadmin.dto.cash.CashListProjection;
import com.ddangme.dmadmin.dto.cash.QCashListProjection;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import javax.persistence.EntityManager;

import java.util.List;

import static com.ddangme.dmadmin.model.admin.QAdmin.admin;
import static com.ddangme.dmadmin.model.cash.QCashCharging.cashCharging;
import static com.ddangme.dmadmin.model.member.QMember.member;

public class CashRepositoryImpl implements CashRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public CashRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }


    @Override
    public Page<CashListProjection> search(Pageable pageable) {
        List<CashListProjection> content = queryFactory
                .select(new QCashListProjection(
                        cashCharging.id,
                        cashCharging.amount,
                        cashCharging.status,
                        cashCharging.member.id.as("memberId"),
                        member.name.as("memberName"),
                        cashCharging.admin.id.as("adminId"),
                        admin.nickname.as("adminName"),
                        cashCharging.requestAt,
                        cashCharging.responseAt))
                .from(cashCharging)
                .innerJoin(member).on(member.id.eq(cashCharging.member.id))
                .leftJoin(admin).on(cashCharging.admin.id.eq(admin.id))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(cashCharging.requestAt.desc())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(cashCharging.count())
                .from(cashCharging);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }
}
