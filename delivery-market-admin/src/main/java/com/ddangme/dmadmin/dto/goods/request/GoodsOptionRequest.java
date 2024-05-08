package com.ddangme.dmadmin.dto.goods.request;

import com.ddangme.dmadmin.model.constants.SaleStatus;
import com.ddangme.dmadmin.model.goods.Goods;
import com.ddangme.dmadmin.model.goods.GoodsOption;
import lombok.Data;

@Data
public class GoodsOptionRequest {
    private Long id;
    private String name;
    private Long price;
    private Long discountPrice;
    private Integer discountPercent;;
    private Long amount;
    private SaleStatus saleStatus;

    public GoodsOption toEntity(Goods goods) {
        return new GoodsOption(
                goods,
                name,
                price,
                discountPrice,
                discountPercent,
                amount,
                saleStatus
        );
    }
}
