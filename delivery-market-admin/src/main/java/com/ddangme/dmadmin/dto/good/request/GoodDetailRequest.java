package com.ddangme.dmadmin.dto.good.request;

import com.ddangme.dmadmin.model.constants.PackagingType;
import com.ddangme.dmadmin.model.good.Good;
import com.ddangme.dmadmin.model.good.GoodDetail;
import lombok.Data;

@Data
public class GoodDetailRequest {
    private Long id;
    private String origin;
    private PackagingType packagingType;
    private String weightVolume;
    private String allergyInfo;
    private String guidelines;
    private String expiryDate;
    private String description;

    public GoodDetail toEntity(Good good) {
        return new GoodDetail(
                good,
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
