package com.ddangme.dm.model.order;

import com.ddangme.dm.model.good.Good;
import com.ddangme.dm.model.good.GoodOption;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderGood {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @Setter
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

    public OrderGood(GoodOption option, Integer quantity) {
        this.good = option.getGood();
        this.option = option;
        this.price = option.getPrice() * quantity;
        if (option.getDiscountPrice() != null && option.getDiscountPrice() != 0) {
            this.discountPrice = option.getDiscountPrice() * quantity;
        }
        this.quantity = quantity;
    }

    public void cancelOrder() {
        option.plusQuantity(quantity);
    }
}
