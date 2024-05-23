package com.ddangme.dm.model.order;

import com.ddangme.dm.model.constants.DeliveryStatus;
import com.ddangme.dm.model.member.Member;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @ToString.Exclude
    private Member member;

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
}
