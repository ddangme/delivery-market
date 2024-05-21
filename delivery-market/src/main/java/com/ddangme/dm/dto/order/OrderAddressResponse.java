package com.ddangme.dm.dto.order;

import com.ddangme.dm.model.Address;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderAddressResponse {

    private Long id;
    private String name;
    private String phone;
    private String road;
    private String detail;
    private Boolean main;

    public OrderAddressResponse(Address address) {
        this.id = address.getId();
        this.name = address.getRecipientName();
        this.phone = address.getRecipientPhone();
        this.road = address.getRoad();
        this.detail = address.getDetail();
        this.main = address.getMain();
    }
}
