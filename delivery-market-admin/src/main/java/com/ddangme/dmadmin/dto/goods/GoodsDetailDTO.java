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

    public GoodsDetailDTO(String origin, PackagingType packagingType, String weightVolume, String allergyInfo, String guidelines, String expiryDate, String description) {
        this.origin = origin;
        this.packagingType = packagingType;
        this.weightVolume = weightVolume;
        this.allergyInfo = allergyInfo;
        this.guidelines = guidelines;
        this.expiryDate = expiryDate;
        this.description = description;
    }

//    public static GoodsDetailDTO fromEntity(GoodsDetail entity) {
//        return new GoodsDetailDTO(
//                entity.getId(),
//                GoodsDTO.fromEntity(entity.getGoods()),
//                entity.getOrigin(),
//                entity.getPackagingType(),
//                entity.getWeightVolume(),
//                entity.getAllergyInfo(),
//                entity.getGuidelines(),
//                entity.getExpiryDate(),
//                entity.getDescription()
//        );
//    }
}
