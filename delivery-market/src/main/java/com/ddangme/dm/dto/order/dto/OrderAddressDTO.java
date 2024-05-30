package com.ddangme.dm.dto.order.dto;

import com.ddangme.dm.model.order.OrderAddress;
import lombok.Data;

@Data
public class OrderAddressDTO {

    private String name;
    private String phone;
    private String address;

    public OrderAddressDTO(OrderAddress entity) {
        this.name = entity.getName();
        this.phone = parsePhone(entity.getPhone());
        this.address = parseAddress(entity);
    }

    private String parsePhone(String phone) {
        return phone.substring(0, 7);
    }

    private String parseAddress(OrderAddress entity) {
        return "(" + entity.getZipcode() + ")" +
                entity.getRoad() + " " +
                entity.getDetail();
    }
}
