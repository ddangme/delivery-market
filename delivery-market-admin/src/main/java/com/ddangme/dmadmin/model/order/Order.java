package com.ddangme.dmadmin.model.order;

import com.ddangme.dmadmin.exception.DMAdminException;
import com.ddangme.dmadmin.exception.ErrorCode;
import com.ddangme.dmadmin.model.constants.DeliveryStatus;
import com.ddangme.dmadmin.model.member.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity(name = "Orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @ToString.Exclude
    private Member member;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    private Long totalPrice;
    private Long totalDiscountPrice;
    private Long deliveryPrice;
    private Long couponDiscountPrice;
    private Long point;
    private Long cash;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDateTime createdAt;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private OrderAddress orderAddress;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderGood> goods;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderDelivery> deliveries = new ArrayList<>();

    public void setDeliveryStatus(DeliveryStatus deliveryStatus, String currentLocation) {
        validateDeliveryStatus(deliveryStatus);
        deliveries.add(new OrderDelivery(this, currentLocation));
        this.deliveryStatus = deliveryStatus;
    }

    private void validateDeliveryStatus(DeliveryStatus deliveryStatus) {
        if (this.deliveryStatus.equals(DeliveryStatus.CANCELLED)) {
            throw new DMAdminException(ErrorCode.IS_NON_MODIFIABLE_ORDER_DELIVERY_STATUS);
        }

        if (this.deliveryStatus.equals(DeliveryStatus.PENDING) && isReturnProcess(deliveryStatus)) {
            throw new DMAdminException(ErrorCode.IS_NON_MODIFIABLE_ORDER_DELIVERY_STATUS);
        }

        if (this.deliveryStatus.equals(DeliveryStatus.DELIVERED) && isReturnProcess(deliveryStatus)) {
            throw new DMAdminException(ErrorCode.IS_NON_MODIFIABLE_ORDER_DELIVERY_STATUS);
        }

        if (this.deliveryStatus.equals(DeliveryStatus.DELIVERED) && deliveryStatus.equals(DeliveryStatus.SHIPPED)) {
            throw new DMAdminException(ErrorCode.IS_NON_MODIFIABLE_ORDER_DELIVERY_STATUS);
        }

        if (this.deliveryStatus.equals(DeliveryStatus.RETURNED) && isNotReturnProcess(deliveryStatus)) {
            throw new DMAdminException(ErrorCode.IS_NON_MODIFIABLE_ORDER_DELIVERY_STATUS);
        }

        if (this.deliveryStatus.equals(DeliveryStatus.RETURNED) && deliveryStatus.equals(DeliveryStatus.DELIVERED)) {
            throw new DMAdminException(ErrorCode.IS_NON_MODIFIABLE_ORDER_DELIVERY_STATUS);
        }

        if (isNotReturnProcess(this.deliveryStatus) && isReturnProcess(deliveryStatus)) {
            throw new DMAdminException(ErrorCode.IS_NON_MODIFIABLE_ORDER_DELIVERY_STATUS);
        }

        if (isReturnProcess(this.deliveryStatus) && isNotReturnProcess(deliveryStatus)) {
            throw new DMAdminException(ErrorCode.IS_NON_MODIFIABLE_ORDER_DELIVERY_STATUS);
        }
    }

    private boolean isReturnProcess(DeliveryStatus deliveryStatus) {
        return deliveryStatus.equals(DeliveryStatus.RETURNED) || deliveryStatus.equals(DeliveryStatus.RETURN_PROCESSED);
    }

    private boolean isNotReturnProcess(DeliveryStatus deliveryStatus) {
        return deliveryStatus.equals(DeliveryStatus.SHIPPED) || deliveryStatus.equals(DeliveryStatus.DELIVERED);
    }
}
