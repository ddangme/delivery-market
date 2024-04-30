package com.ddangme.dmadmin.dto.goods;

import com.ddangme.dmadmin.model.constants.PackagingType;
import com.ddangme.dmadmin.model.goods.GoodsDetail;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GoodsDetailDTO {
    private Long id;
    private GoodsDTO goodsDTO;
    private String origin;
    private PackagingType packagingType;
    private String weightVolume;
    private String allergyInfo;
    private String guidelines;
    private String expiryDate;
    private String description;

    public static GoodsDetailDTO fromEntity(GoodsDetail entity) {
        return new GoodsDetailDTO(
                entity.getId(),
                GoodsDTO.fromEntity(entity.getGoods()),
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
