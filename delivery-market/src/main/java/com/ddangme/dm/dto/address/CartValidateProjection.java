package com.ddangme.dm.dto.address;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class CartValidateProjection {

    private Long id;
    private Integer buyQuantity;
    private Integer remainQuantity;
    private Long price;
    private Long discountPrice;

    @QueryProjection

    public CartValidateProjection(Long id, Integer buyQuantity, Integer remainQuantity, Long price, Long discountPrice) {
        this.id = id;
        this.buyQuantity = buyQuantity;
        this.remainQuantity = remainQuantity;
        this.price = price;
        this.discountPrice = discountPrice;
    }
}
