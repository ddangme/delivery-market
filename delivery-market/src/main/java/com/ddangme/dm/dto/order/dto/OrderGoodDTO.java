package com.ddangme.dm.dto.order.dto;

import com.ddangme.dm.model.order.OrderGood;
import lombok.Data;

@Data
public class OrderGoodDTO {

    private Long id;
    private String name;
    private String photo;
    private Long price;
    private Long discountPrice;
    private Integer quantity;
    private String status;
    private Long reviewId;

    public OrderGoodDTO(OrderGood entity) {
        this.id = entity.getGood().getId();
        this.name = entity.getOption().getName();
        this.photo = entity.getGood().getPhotoStoreFileName();
        this.price = entity.getPrice();
        this.discountPrice = entity.getDiscountPrice();
        this.quantity = entity.getQuantity();
        this.status = entity.getOption().getSaleStatus().getStatus();
    }
}
