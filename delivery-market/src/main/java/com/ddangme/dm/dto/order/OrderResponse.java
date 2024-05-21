package com.ddangme.dm.dto.order;

import com.ddangme.dm.model.Address;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class OrderResponse {

    private List<OrderCartProjection> good;
    private AddressResponse address;
    private OrderPayResponse pay;
    private Long usePoint;
    private Long useCash;
    private Long totalGoodPrice;

    public OrderResponse(Address address, OrderPayResponse pay) {
        this.address = new AddressResponse(address);
        this.pay = pay;
        usePoint = 0L;
        useCash = 0L;
        totalGoodPrice = 0L;
    }

    @Data
    @AllArgsConstructor
    class AddressResponse {
        private Long id;
        private String name;
        private String phone;
        private String road;
        private String detail;
        private Boolean main;

        private AddressResponse(Address address) {
            this.id = address.getId();
            this.name = address.getRecipientName();
            this.phone = address.getRecipientPhone();
            this.road = address.getRoad();
            this.detail = address.getDetail();
            this.main = address.getMain();
        }
    }

    public void setGood(List<OrderCartProjection> good) {
        calTotalGoodPrice(good);
        calUseMoney();
        this.good = good;
    }

    private void calUseMoney() {
        if (totalGoodPrice > pay.getPoint()) {
            usePoint = pay.getPoint();
            useCash = totalGoodPrice - usePoint;
        } else {
            usePoint = totalGoodPrice;
        }
    }

    private void calTotalGoodPrice(List<OrderCartProjection> goods) {
        for (OrderCartProjection value : goods) {
            if (value.getDiscountPrice() == null) {
                totalGoodPrice += value.getPrice();
            } else {
                totalGoodPrice += value.getDiscountPrice();
            }
        }
    }
}
