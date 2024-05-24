package com.ddangme.dm.model.order;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @Setter
    private Order order;

    private String name;
    private String phone;
    private String road;
    private String detail;
    private Integer zipcode;

    public OrderAddress(String name, String phone, String road, String detail, Integer zipcode) {
        this.name = name;
        this.phone = phone;
        this.road = road;
        this.detail = detail;
        this.zipcode = zipcode;
    }
}
