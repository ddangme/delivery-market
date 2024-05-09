package com.ddangme.dmadmin.model.good;

import com.ddangme.dmadmin.model.AuditingFields;
import com.ddangme.dmadmin.model.constants.SaleStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "DELETED_AT IS NULL")
@AllArgsConstructor
public class GoodOption extends AuditingFields {

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
    private Long amount;

    @Enumerated(EnumType.STRING)
    private SaleStatus saleStatus;

    public GoodOption(Good good, String name, Long price, Long discountPrice, Integer discountPercent, Long amount, SaleStatus saleStatus) {
        this.good = good;
        this.name = name;
        this.price = price;
        this.discountPrice = discountPrice;
        this.discountPercent = discountPercent;
        this.amount = amount;
        this.saleStatus = saleStatus;
    }


    public void edit(String name, Long price, Long discountPrice, Integer discountPercent, Long amount, SaleStatus saleStatus) {
        this.name = name;
        this.price = price;
        this.discountPrice = discountPrice;
        this.discountPercent = discountPercent;
        this.amount = amount;
        this.saleStatus = saleStatus;
    }

}
