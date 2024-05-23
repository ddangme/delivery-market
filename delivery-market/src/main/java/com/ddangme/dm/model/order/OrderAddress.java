package com.ddangme.dm.model.order;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class OrderAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private String name;
    private String phone;
    private String road;
    private String detail;
    private String zipcode;
}
