package com.ddangme.dmadmin.dto.goods.request;

import com.ddangme.dmadmin.model.constants.PackagingType;
import com.ddangme.dmadmin.model.goods.Goods;
import com.ddangme.dmadmin.model.goods.GoodsDetail;
import lombok.Data;

@Data
public class GoodsDetailRequest {
    private Long id;
    private String origin;
    private PackagingType packagingType;
    private String weightVolume;
    private String allergyInfo;
    private String guidelines;
    private String expiryDate;
    private String description;

    public GoodsDetail toEntity(Goods goods) {
        return new GoodsDetail(
                goods,
                origin,
                packagingType,
                weightVolume,
                allergyInfo,
                guidelines,
                expiryDate,
                description
        );
    }
}
