package com.ddangme.dmadmin.dto.goods.request;


import com.ddangme.dmadmin.model.constants.SaleStatus;
import com.ddangme.dmadmin.model.constants.UploadFile;
import com.ddangme.dmadmin.model.goods.Category;
import com.ddangme.dmadmin.model.goods.Goods;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@ToString
@Data
public class GoodsRequest {

    private Long id;
    private String name;
    private String summary;
    private Long categoryId;
    private Long price;
    private Long discountPrice;
    private Integer discountPercent;
    private SaleStatus saleStatus;

    private GoodsDetailRequest goodsDetail;
    private List<GoodsOptionRequest> goodsOptions;

    public Goods toEntity(Category category, UploadFile photo) {
        Goods good = new Goods(
                category,
                name,
                summary,
                price,
                discountPrice,
                discountPercent,
                saleStatus,
                photo
        );

        good.saveDetail(goodsDetail.toEntity(good));
        good.saveOptions(goodsOptions.stream()
                .map(option -> option.toEntity(good))
                .toList());

        return good;
    }
}


