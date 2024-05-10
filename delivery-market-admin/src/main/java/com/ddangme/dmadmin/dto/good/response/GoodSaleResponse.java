package com.ddangme.dmadmin.dto.good.response;

import com.ddangme.dmadmin.model.constants.SaleStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class GoodSaleResponse {
    private Long id;
    private String name;
    private String summary;
    private Long price;
    private Long discountPrice;
    private Integer discountPercent;
    private SaleStatus saleStatus;
    private String photo;

    @QueryProjection
    public GoodSaleResponse(Long id, String name, String summary, Long price, Long discountPrice, Integer discountPercent, SaleStatus saleStatus, String photo) {
        this.id = id;
        this.name = name;
        this.summary = summary;
        this.price = price;
        this.discountPrice = discountPrice;
        this.discountPercent = discountPercent;
        this.saleStatus = saleStatus;
        this.photo = photo;
    }
}
