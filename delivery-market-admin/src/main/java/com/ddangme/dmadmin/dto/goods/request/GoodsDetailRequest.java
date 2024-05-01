package com.ddangme.dmadmin.dto.goods.request;

import lombok.Data;

@Data
public class GoodsDetailRequest {
    private String origin;
    private String packagingType;

    private String weightVolume;

    private String allergyInfo;

    private String guidelines;

    private String expiryDate;

    private String description;
}
