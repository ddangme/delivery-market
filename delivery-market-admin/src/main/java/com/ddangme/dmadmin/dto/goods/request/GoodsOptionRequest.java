package com.ddangme.dmadmin.dto.goods.request;

import lombok.Data;

@Data
public class GoodsOptionRequest {
    private Long optionPrice;
    private Long optionSalePrice;
    private Long optionSalePercent;;
    private Long optionAmount;

}
