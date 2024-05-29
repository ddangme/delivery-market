package com.ddangme.dmadmin.dto.order;

import com.ddangme.dmadmin.model.constants.DeliveryStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class OrderResponse {

    private Long id;
    private String name;
    private Long price;
    private String status;
    private String member;

    @QueryProjection
    public OrderResponse(Long id, String name, Long price, DeliveryStatus deliveryStatus, String member) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.status = deliveryStatus.getStatus();
        this.member = member;
    }
}
