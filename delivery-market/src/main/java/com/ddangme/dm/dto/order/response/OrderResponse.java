package com.ddangme.dm.dto.order.response;

import com.ddangme.dm.dto.order.OrderCartProjection;
import com.ddangme.dm.model.Address;
import lombok.Data;

import java.util.List;

@Data
public class OrderResponse {

    private List<OrderCartProjection> good;
    private OrderAddressResponse address;
    private OrderPayResponse pay;
    private Long usePoint;
    private Long useCash;
    private Long totalGoodPrice;
    private Long totalDiscountPrice;
    private Long deliveryPrice;
    private Long totalPrice;

    public OrderResponse(Address address, OrderPayResponse pay) {
        this.address = new OrderAddressResponse(address);
        this.pay = pay;
        usePoint = 0L;
        useCash = 0L;
        totalGoodPrice = 0L;
        totalDiscountPrice = 0L;
        deliveryPrice = 3000L;
        totalPrice = 0L;
    }

    public void setGood(List<OrderCartProjection> good) {
        this.good = good;
        calTotalGoodPrice();
        calTotalDiscountPrice();
        calDeliveryPrice();
        totalPrice = totalGoodPrice + deliveryPrice;
        calUseMoney();
    }

    private void calDeliveryPrice() {
        if (totalGoodPrice >= 40_000) {
            deliveryPrice = 0L;
        }
    }

    private void calTotalDiscountPrice() {
        for (OrderCartProjection value : good) {
            if (value.getDiscountPrice() != null) {
                totalDiscountPrice += (value.getPrice() - value.getDiscountPrice());
            }
        }
    }

    private void calUseMoney() {
        if (totalPrice > pay.getPoint()) {
            usePoint = pay.getPoint();
            useCash = totalPrice - usePoint;
        } else {
            usePoint = totalPrice;
        }
    }

    private void calTotalGoodPrice() {
        for (OrderCartProjection value : good) {
            if (value.getDiscountPrice() == null) {
                totalGoodPrice += value.getPrice();
            } else {
                totalGoodPrice += value.getDiscountPrice();
            }
        }
    }
}
