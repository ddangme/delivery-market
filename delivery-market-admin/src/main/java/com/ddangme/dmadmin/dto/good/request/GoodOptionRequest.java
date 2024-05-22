package com.ddangme.dmadmin.dto.good.request;

import com.ddangme.dmadmin.model.constants.SaleStatus;
import com.ddangme.dmadmin.model.good.Good;
import com.ddangme.dmadmin.model.good.GoodOption;
import lombok.Data;

@Data
public class GoodOptionRequest {
    private Long id;
    private String name;
    private Long price;
    private Long discountPrice;
    private Integer discountPercent;
    private Long quantity;
    private SaleStatus saleStatus;

    public GoodOption toEntity(Good good) {
        return new GoodOption(
                good,
                name,
                price,
                discountPrice,
                discountPercent,
                quantity,
                saleStatus
        );
    }
}
