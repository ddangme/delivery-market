package com.ddangme.dm.dto.cart;

import com.ddangme.dm.model.constants.PackagingType;
import com.ddangme.dm.model.constants.SaleStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class CartListProjection {
    private Long id;
    private Long optionId;
    private String optionName;
    private String goodName;
    private Integer count;
    private String photo;
    private PackagingType packagingType;
    private SaleStatus saleStatus;
    private Boolean checkStatus;
    private Long price;
    private Long discountPrice;

    @QueryProjection
    public CartListProjection(Long id, Long optionId, String optionName, String goodName, Integer count, String photo, PackagingType packagingType, SaleStatus saleStatus, Boolean checkStatus, Long price, Long discountPrice) {
        this.id = id;
        this.optionId = optionId;
        this.optionName = optionName;
        this.goodName = goodName;
        this.count = count;
        this.photo = photo;
        this.packagingType = packagingType;
        this.saleStatus = saleStatus;
        this.checkStatus = checkStatus;
        this.price = price;
        this.discountPrice = discountPrice;
    }

    public void calculateTotalPrice() {
        this.price = price * count;
        if (discountPrice != null) {
            this.discountPrice = discountPrice * count;
        }
    }

    public boolean isCheck() {
        return checkStatus;
    }
}
