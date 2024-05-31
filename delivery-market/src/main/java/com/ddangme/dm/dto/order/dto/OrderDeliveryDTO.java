package com.ddangme.dm.dto.order.dto;

import com.ddangme.dm.model.order.OrderDelivery;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderDeliveryDTO {
    private LocalDateTime createdAt;
    private String currentLocation;
    private String deliveryStatus;

    public OrderDeliveryDTO(OrderDelivery delivery) {
        this.createdAt = delivery.getCreatedAt();
        this.currentLocation = delivery.getCurrentLocation();
        this.deliveryStatus = delivery.getDeliveryStatus().getStatus();
    }
}
