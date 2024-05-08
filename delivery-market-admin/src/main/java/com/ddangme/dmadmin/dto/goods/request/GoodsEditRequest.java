package com.ddangme.dmadmin.dto.goods.request;

import com.ddangme.dmadmin.model.constants.SaleStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@ToString
@Data
@AllArgsConstructor
public class GoodsEditRequest {
    private Long id;
    private String name;
    private String summary;
    private Long categoryId;
    private Long price;
    private Long discountPrice;
    private Integer discountPercent;
    private SaleStatus saleStatus;

    private GoodsEditDetailRequest goodsDetail;
    private List<GoodsEditOptionRequest> goodsOptions;

}
