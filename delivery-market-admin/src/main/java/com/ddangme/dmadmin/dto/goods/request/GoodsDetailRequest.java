package com.ddangme.dmadmin.dto.goods.request;

import com.ddangme.dmadmin.dto.goods.GoodsDetailDTO;
import com.ddangme.dmadmin.model.constants.PackagingType;
import lombok.Data;

@Data
public class GoodsDetailRequest {
    private String origin;
    private PackagingType packagingType;
    private String weightVolume;
    private String allergyInfo;
    private String guidelines;
    private String expiryDate;
    private String description;

    public GoodsDetailDTO toDTO() {
        return new GoodsDetailDTO(
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
