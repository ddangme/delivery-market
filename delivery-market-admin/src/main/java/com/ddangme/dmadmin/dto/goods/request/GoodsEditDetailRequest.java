package com.ddangme.dmadmin.dto.goods.request;

import com.ddangme.dmadmin.model.constants.PackagingType;
import lombok.Data;

@Data
public class GoodsEditDetailRequest {
    private Long id;
    private String origin;
    private PackagingType packagingType;
    private String weightVolume;
    private String allergyInfo;
    private String guidelines;
    private String expiryDate;
    private String description;
}
