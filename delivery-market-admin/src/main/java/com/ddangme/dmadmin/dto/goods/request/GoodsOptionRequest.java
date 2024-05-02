package com.ddangme.dmadmin.dto.goods.request;

import com.ddangme.dmadmin.dto.goods.GoodsOptionDTO;
import com.ddangme.dmadmin.model.constants.SaleStatus;
import lombok.Data;

@Data
public class GoodsOptionRequest {
    private String name;
    private Long price;
    private Long discountPrice;
    private Integer discountPercent;;
    private Long amount;
    private SaleStatus saleStatus;

    public GoodsOptionDTO toDTO() {
        return new GoodsOptionDTO(
                name,
                price,
                discountPrice,
                discountPercent,
                amount,
                saleStatus
        );
    }
}
