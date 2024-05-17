package com.ddangme.dm.dto.cart.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CartRequest {

    private Long optionId;
    private Integer count;

}
