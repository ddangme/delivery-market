package com.ddangme.dmadmin.dto.good.response;

import com.ddangme.dmadmin.model.constants.PackagingType;
import com.ddangme.dmadmin.model.good.GoodDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class GoodDetailResponse {

    private Long id;
    private String origin;
    private PackagingType packagingType;
    private String weightVolume;
    private String allergyInfo;
    private String guidelines;
    private String expiryDate;
    private String description;

    public static GoodDetailResponse fromEntity(GoodDetail entity) {
        return new GoodDetailResponse(
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
