package com.ddangme.dmadmin.dto.goods.response;

import com.ddangme.dmadmin.model.constants.PackagingType;
import com.ddangme.dmadmin.model.goods.GoodsDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class GoodsDetailResponse {

    private Long id;
    private String origin;
    private PackagingType packagingType;
    private String weightVolume;
    private String allergyInfo;
    private String guidelines;
    private String expiryDate;
    private String description;

    public static GoodsDetailResponse fromEntity(GoodsDetail entity) {
        return new GoodsDetailResponse(
                entity.getId(),
                entity.getOrigin(),
                entity.getPackagingType(),
                entity.getWeightVolume(),
                entity.getAllergyInfo(),
                entity.getGuidelines(),
                entity.getExpiryDate(),
                entity.getDescription()
        );
    }
}
