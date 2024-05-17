package com.ddangme.dm.dto.cart.request;

import lombok.Data;

@Data
public class CartChangeCountRequest {

    private Long id;
    private Integer count;

}
