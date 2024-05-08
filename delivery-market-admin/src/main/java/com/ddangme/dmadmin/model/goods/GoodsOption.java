package com.ddangme.dmadmin.model.goods;

import com.ddangme.dmadmin.model.Admin;
import com.ddangme.dmadmin.model.AuditingFields;
import com.ddangme.dmadmin.model.constants.SaleStatus;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "DELETED_AT IS NULL")
@AllArgsConstructor
public class GoodsOption extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "goods_option_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goods_id")
    private Goods goods;
    private String name;
    private Long price;
    private Long discountPrice;
    private Integer discountPercent;
    private Long amount;

    @Enumerated(EnumType.STRING)
    private SaleStatus saleStatus;

    public GoodsOption(Goods goods, String name, Long price, Long discountPrice, Integer discountPercent, Long amount, SaleStatus saleStatus) {
        this.goods = goods;
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
