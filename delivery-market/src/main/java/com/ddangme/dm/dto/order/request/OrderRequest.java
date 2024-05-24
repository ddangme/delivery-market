package com.ddangme.dm.dto.order.request;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrderRequest {

    private List<Long> cartIds = new ArrayList<>();
    private Long addressId;
}
