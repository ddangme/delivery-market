package com.ddangme.dm.dto.good;

import com.ddangme.dm.model.constants.PackagingType;
import com.ddangme.dm.model.constants.SaleStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CartProjection {

    private Long id;
    private Long optionId;
    private String optionName;
    private String goodName;
    private Integer count;
    private String photo;
    private PackagingType packagingType;
    private SaleStatus saleStatus;
    private Boolean checkStatus;

    @QueryProjection
    public CartProjection(Long id, Long optionId, String optionName, String goodName, Integer count, String photo, PackagingType packagingType, SaleStatus saleStatus, Boolean checkStatus) {
        this.id = id;
        this.optionId = optionId;
        this.optionName = optionName;
        this.goodName = goodName;
        this.count = count;
        this.photo = photo;
        this.packagingType = packagingType;
        this.saleStatus = saleStatus;
        this.checkStatus = checkStatus;
    }
}
