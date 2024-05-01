package com.ddangme.dmadmin.dto.goods.request;

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
}
