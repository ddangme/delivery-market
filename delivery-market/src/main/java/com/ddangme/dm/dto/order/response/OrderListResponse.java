package com.ddangme.dm.dto.order.response;

import com.ddangme.dm.model.order.Order;
import com.ddangme.dm.model.order.OrderGood;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class OrderListResponse {


    private Long id;
    private String goodName;
    private Long price;
    private String status;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDateTime createdAt;

    public static OrderListResponse fromEntity(Order entity) {
        return new OrderListResponse(
                entity.getId(),
                getGoodName(entity.getGoods()),
                entity.getCash(),
                entity.getDeliveryStatus().getStatus(),
                entity.getCreatedAt()
        );
    }

    private static String getGoodName(List<OrderGood> goods) {
        if (goods.isEmpty()) {
            return null;
        }

        String firstName = goods.get(0).getGood().getName();

        if (goods.size() == 1) {
            return firstName;
        } else {
            return firstName + " 외 " + (goods.size() - 1) + "건";
        }
    }


}
