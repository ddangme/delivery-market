package com.ddangme.dm.dto.order;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class OrderAddressProjection {

    private Long id;
    private String name;
    private String phone;
    private String road;
    private String detail;
    private Boolean main;

    @QueryProjection
    public OrderAddressProjection(Long id, String name, String phone, String road, String detail) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.road = road;
        this.detail = detail;
    }

    @QueryProjection
    public OrderAddressProjection(Long id, String name, String phone, String road, String detail, Boolean main) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.road = road;
        this.detail = detail;
        this.main = main;
    }
}
