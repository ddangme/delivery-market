package com.ddangme.dmadmin.dto.good.response;

import com.ddangme.dmadmin.model.constants.SaleStatus;
import com.ddangme.dmadmin.model.good.GoodOption;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

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
