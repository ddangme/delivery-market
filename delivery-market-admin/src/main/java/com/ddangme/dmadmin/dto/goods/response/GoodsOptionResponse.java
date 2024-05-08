package com.ddangme.dmadmin.dto.goods.response;

import com.ddangme.dmadmin.model.constants.SaleStatus;
import com.ddangme.dmadmin.model.goods.GoodsOption;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class GoodsOptionResponse {

    private Long id;
    private String name;
    private Long price;
    private Long discountPrice;
    private Integer discountPercent;
    private Long amount;
    private SaleStatus saleStatus;

    public static GoodsOptionResponse fromEntity(GoodsOption entity) {
        return new GoodsOptionResponse(
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
