package com.ddangme.dmadmin.model.goods;

import com.ddangme.dmadmin.model.AuditingFields;
import com.ddangme.dmadmin.model.constants.SaleStatus;
import com.ddangme.dmadmin.model.constants.UploadFile;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Goods extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "goods_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    private String name;

    private String summary;

    private Long price;

    private Long salePrice;

    private Integer salePercent;

    @Enumerated(EnumType.STRING)
    private SaleStatus saleStatus;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "uploadFileName", column = @Column(name = "photo_upload_file_name")),
            @AttributeOverride(name = "storeFileName", column = @Column(name = "photo_store_file_name"))
    })
    private UploadFile photo;

    @OneToOne(fetch = FetchType.LAZY)
    @Transient
    private GoodsDetail goodsDetail;

    @ToString.Exclude
    @OrderBy("createdAt DESC")
    @OneToMany(mappedBy = "goods", cascade = CascadeType.ALL)
    private Set<GoodsOption> goodsOption;

}
