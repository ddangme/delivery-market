package com.ddangme.dm.dto.good.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartResponse {

    private Integer count;
    private String message;
}
