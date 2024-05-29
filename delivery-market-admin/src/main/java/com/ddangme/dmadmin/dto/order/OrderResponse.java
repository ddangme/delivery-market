package com.ddangme.dmadmin.dto.order;

import com.ddangme.dmadmin.model.constants.DeliveryStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class OrderResponse {

    private Long id;
    private Long price;
    private String status;
    private String member;
    private LocalDateTime createdAt;

    @QueryProjection
    public OrderResponse(Long id, Long price, DeliveryStatus deliveryStatus, String member, LocalDateTime createdAt) {
        this.id = id;
        this.price = price;
        this.status = deliveryStatus.getStatus();
        this.member = member;
        this.createdAt = createdAt;
    }
}
