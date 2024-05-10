package com.ddangme.dm.dto.good;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class GoodListResponse {
    private Long id;
    private String name;
    private String summary;
    private String photo;
    private Long price;
    private Long discountPrice;
    private Long discountPercent;

    @QueryProjection
    public GoodListResponse(Long id, String name, String summary, String photo, Long price, Long discountPrice, Long discountPercent) {
        this.id = id;
        this.name = name;
        this.summary = summary;
        this.photo = photo;
        this.price = price;
        this.discountPrice = discountPrice;
        this.discountPercent = discountPercent;
    }
}
