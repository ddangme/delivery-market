package com.ddangme.dm.dto.order.dto;

import com.ddangme.dm.model.order.Order;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderDTO {

    private Long id;
    private String deliveryStatus;
    private Long totalPrice;
    private Long totalDiscountPrice;
    private Long deliveryPrice;
    private Long couponDiscountPrice;
    private Long point;
    private Long cash;
    private String memberName;
    private LocalDateTime createdAt;

    public OrderDTO(Order entity) {
        this.id = entity.getId();
        this.deliveryStatus = entity.getDeliveryStatus().getStatus();
        this.totalPrice = entity.getTotalPrice();
        this.totalDiscountPrice = entity.getTotalDiscountPrice();
        this.deliveryPrice = entity.getDeliveryPrice();
        this.couponDiscountPrice = entity.getCouponDiscountPrice();
        this.point = entity.getPoint();
        this.cash = entity.getCash();
        this.memberName = entity.getMember().getName();
        this.createdAt = entity.getCreatedAt();
    }
}
