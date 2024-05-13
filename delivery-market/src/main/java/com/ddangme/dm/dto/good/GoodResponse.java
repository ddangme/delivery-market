package com.ddangme.dm.dto.good;

import com.ddangme.dm.model.constants.SaleStatus;
import com.ddangme.dm.model.good.Good;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class GoodResponse {

    private Long id;
    private String name;
    private String summary;

    private Long price;
    private Long discountPrice;
    private Integer discountPercent;
    private SaleStatus saleStatus;
    private String photo;

    @QueryProjection
    public GoodResponse(Long id, String name, String summary, Long price, Long discountPrice, Integer discountPercent, SaleStatus saleStatus, String photo) {
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
