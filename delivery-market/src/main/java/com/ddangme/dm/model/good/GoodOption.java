package com.ddangme.dm.model.good;

import com.ddangme.dm.exception.DMException;
import com.ddangme.dm.exception.ErrorCode;
import com.ddangme.dm.model.constants.SaleStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class GoodOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "good_id")
    private Good good;

    private String name;
    private Long price;
    private Long discountPrice;
    private Integer discountPercent;
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    private SaleStatus saleStatus;

    public void minusQuantity(Integer buyQuantity) {
        if (quantity - buyQuantity < 0) {
            throw new DMException(ErrorCode.EXIST_NON_ORDER_OPTION);
        }
        this.quantity -= buyQuantity;

        if (quantity == 0) {
            saleStatus = SaleStatus.SOLD_OUT;
        }
    }
}
