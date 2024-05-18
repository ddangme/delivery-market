package com.ddangme.dm.dto.cart;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class CartChangeCountProjection {

    private Long cartId;
    private Long price;
    private Long discountPrice;

    @QueryProjection
    public CartChangeCountProjection(Long cartId, Long price, Long discountPrice) {
        this.cartId = cartId;
        this.price = price;
        this.discountPrice = discountPrice;
    }
}
