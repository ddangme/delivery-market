package com.ddangme.dmadmin.model.goods;

import com.ddangme.dmadmin.model.AuditingFields;
import com.ddangme.dmadmin.model.constants.SaleStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "DELETED_AT IS NULL")
public class GoodsOption extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "goods_option_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goods_id")
    private Goods goods;

    private Long price;
    private Long salePrice;
    private Long salePercent;
    private Long amount;

    @Enumerated(EnumType.STRING)
    private SaleStatus saleStatus;

}
