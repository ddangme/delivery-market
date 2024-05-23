package com.ddangme.dm.dto.address;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class CartValidateProjection {

    private Long id;
    private Integer buyQuantity;
    private Integer remainQuantity;

    @QueryProjection
    public CartValidateProjection(Long id, Integer buyQuantity, Integer remainQuantity) {
        this.id = id;
        this.buyQuantity = buyQuantity;
        this.remainQuantity = remainQuantity;
    }
}
