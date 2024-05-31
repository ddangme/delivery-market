package com.ddangme.dm.model.order;

import com.ddangme.dm.constants.DeliveryStatus;
import lombok.Getter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
public class OrderDelivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @ToString.Exclude
    private Order order;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDateTime createdAt;

    private String currentLocation;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

}
