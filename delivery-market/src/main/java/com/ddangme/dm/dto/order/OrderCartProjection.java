package com.ddangme.dm.dto.order;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class OrderCartProjection {

    private Long id;
    private String photo;
    private String optionName;
    private String goodName;
    private Integer optionCount;
    private Long price;
    private Long discountPrice;

    @QueryProjection
    public OrderCartProjection(Long id, String photo, String optionName, String goodName, Integer optionCount, Long price, Long discountPrice) {
        this.id = id;
        this.photo = photo;
        this.optionName = optionName;
        this.goodName = goodName;
        this.optionCount = optionCount;
        this.price = price;
        this.discountPrice = discountPrice;
    }
}
