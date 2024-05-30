package com.ddangme.dm.dto.order.response;

import com.ddangme.dm.dto.order.dto.OrderAddressDTO;
import com.ddangme.dm.dto.order.dto.OrderDTO;
import com.ddangme.dm.dto.order.dto.OrderGoodDTO;
import com.ddangme.dm.model.order.Order;
import com.ddangme.dm.model.order.OrderGood;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrderDetailResponse {
    private OrderDTO order;
    private List<OrderGoodDTO> goods = new ArrayList<>();
    private OrderAddressDTO address;

    public OrderDetailResponse(Order order) {
        this.order = new OrderDTO(order);
        order.getGoods()
                .forEach(good -> this.goods.add(new OrderGoodDTO(good)));
        this.address = new OrderAddressDTO(order.getOrderAddress());
    }
}
