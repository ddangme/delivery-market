package com.ddangme.dm.model.order;

import com.ddangme.dm.constants.DeliveryStatus;
import com.ddangme.dm.exception.DMException;
import com.ddangme.dm.exception.ErrorCode;
import com.ddangme.dm.model.member.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity(name = "Orders")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    @CreatedDate
    private LocalDateTime createdAt;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private OrderAddress orderAddress;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderGood> goods;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderDelivery> deliveries;

    public Order(Member member, OrderAddress orderAddress, List<OrderGood> goods) {
        this.member = member;
        this.deliveryStatus = DeliveryStatus.PENDING;
        setOrderAddress(orderAddress);
        setOrderGoods(goods);
        this.totalPrice = 0L;
        this.totalDiscountPrice = 0L;
        this.couponDiscountPrice = 0L;
        this.deliveryPrice = 3000L;
        this.point = 0L;
        this.cash = 0L;
        calculateMoney();
    }

    public void cancelOrder() {
        if (!deliveryStatus.equals(DeliveryStatus.PENDING)) {
            throw new DMException(ErrorCode.IS_NON_CANCEL_ORDER);
        }

        goods.forEach(OrderGood::cancelOrder);
        deliveryStatus = DeliveryStatus.CANCELLED;
    }
    private void setOrderAddress(OrderAddress orderAddress) {
        orderAddress.setOrder(this);
        this.orderAddress = orderAddress;
    }

    private void setOrderGoods(List<OrderGood> goods) {
        List<OrderGood> addGoods = new ArrayList<>();
        for (OrderGood good : goods) {
            good.setOrder(this);
            addGoods.add(good);
        }
        this.goods = addGoods;
    }

    private void calculateMoney() {
        for (OrderGood good : goods) {
            totalPrice += good.getPrice();

            if (good.getDiscountPrice() == null || good.getDiscountPrice() == 0) {
                totalDiscountPrice += good.getPrice();
            } else {
                totalDiscountPrice += good.getDiscountPrice();
            }
        }

        if (totalDiscountPrice >= 40_000) {
            this.deliveryPrice = 0L;
        }

        usePointAndCash();
    }

    private void usePointAndCash() {
        long payMoney = totalDiscountPrice + deliveryPrice;
        member.canBuy(payMoney);
        Long remainMoney = member.usePoint(payMoney);
        point = payMoney - remainMoney;
        if (remainMoney == 0) {
            cash = 0L;
        } else {
            member.useCash(remainMoney);
            cash = remainMoney;
        }
    }

    public void validateWriteReview() {
        LocalDateTime now = LocalDateTime.now();
        if (!createdAt.isAfter(now.minusMonths(1))) {
            throw new DMException(ErrorCode.TIME_OVER_REVIEW);
        }
    }
}
