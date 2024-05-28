package com.ddangme.dm.model.good;

import com.ddangme.dm.constants.SaleStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Good {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    private String summary;
    private Long price;
    private Long discountPrice;
    private Integer discountPercent;

    @Enumerated(EnumType.STRING)
    private SaleStatus saleStatus;

    private String photoStoreFileName;

    @OneToOne(mappedBy = "good")
    private GoodDetail goodDetail;

    @OneToMany(mappedBy = "good")
    private List<GoodOption> goodOptions;

}
