package com.ddangme.dmadmin.dto.good.response;

import com.ddangme.dmadmin.model.constants.SaleStatus;
import com.ddangme.dmadmin.model.good.GoodOption;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Data
@AllArgsConstructor
public class GoodOptionResponse {

    private Long id;
    private String name;
    private Long price;
    private Long discountPrice;
    private Integer discountPercent;
    private Long amount;
    private SaleStatus saleStatus;

    public static GoodOptionResponse fromEntity(GoodOption entity) {
        return new GoodOptionResponse(
                entity.getId(),
                entity.getName(),
                entity.getPrice(),
                entity.getDiscountPrice(),
                entity.getDiscountPercent(),
                entity.getAmount(),
                entity.getSaleStatus()
        );
    }
}
