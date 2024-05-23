package com.ddangme.dm.model.order;

import com.ddangme.dm.model.good.Good;
import com.ddangme.dm.model.good.GoodOption;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class OrderGood {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "good_id")
    private Good good;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id")
    private GoodOption option;

    private Long price;
    private Long discountPrice;
    private Integer quantity;

}
