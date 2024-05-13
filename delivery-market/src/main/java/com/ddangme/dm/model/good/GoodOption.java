package com.ddangme.dm.model.good;

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

    private String name;
    private Long price;
    private Long discountPrice;
    private Integer discountPercent;
    private Long amount;

    @Enumerated(EnumType.STRING)
    private SaleStatus saleStatus;
}
