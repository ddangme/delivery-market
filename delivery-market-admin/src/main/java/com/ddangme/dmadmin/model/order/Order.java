package com.ddangme.dmadmin.model.order;

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
    private List<OrderDelivery> deliveries;
}
