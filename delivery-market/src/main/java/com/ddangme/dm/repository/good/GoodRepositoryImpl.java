package com.ddangme.dm.repository.good;

import com.ddangme.dm.dto.good.GoodResponse;
import com.ddangme.dm.dto.good.QGoodResponse;
import com.ddangme.dm.model.constants.SaleStatus;
import com.ddangme.dm.model.good.Good;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import javax.persistence.EntityManager;
import java.util.List;

import static com.ddangme.dm.model.good.QGood.good;

public class GoodRepositoryImpl implements GoodRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public GoodRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public Page<GoodResponse> findSaleStatusGood(Pageable pageable) {
        List<GoodResponse> content = queryFactory
                .select(new QGoodResponse(
                        good.id,
                        good.name,
                        good.summary,
                        good.price,
                        good.discountPrice,
                        good.discountPercent,
                        good.saleStatus,
                        good.photoStoreFileName
                ))
                .from(good)
                .where(good.saleStatus.notIn(SaleStatus.END))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(good.id.asc())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(good.count())
                .from(good)
                .where(good.saleStatus.notIn(SaleStatus.END));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }
}
