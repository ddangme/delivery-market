package com.ddangme.dm.dto.cart.request;

import lombok.Data;

@Data
public class CartChangeCheckRequest {

    private Long id;
    private Boolean checkStatus;

}
