package com.ddangme.dmadmin.model.goods;

import com.ddangme.dmadmin.model.AuditingFields;
import com.ddangme.dmadmin.model.constants.SaleStatus;
import com.ddangme.dmadmin.model.constants.UploadFile;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "DELETED_AT IS NULL")
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

    private Long discountPrice;

    private Integer discountPercent;

    @Enumerated(EnumType.STRING)
    private SaleStatus saleStatus;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "uploadFileName", column = @Column(name = "photo_upload_file_name")),
            @AttributeOverride(name = "storeFileName", column = @Column(name = "photo_store_file_name"))
    })
    @Setter
    private UploadFile photo;

    @OneToOne(mappedBy = "goods", cascade = CascadeType.ALL)
    private GoodsDetail goodsDetail;

    @ToString.Exclude
    @OrderBy("createdAt DESC")
    @OneToMany(mappedBy = "goods", cascade = CascadeType.ALL)
    private Set<GoodsOption> goodsOption;

    public Goods(Category category, String name, String summary, Long price, Long discountPrice, Integer discountPercent, SaleStatus saleStatus, UploadFile photo) {
        this.category = category;
        this.name = name;
        this.summary = summary;
        this.price = price;
        this.discountPrice = discountPrice;
        this.discountPercent = discountPercent;
        this.saleStatus = saleStatus;
        this.photo = photo;
    }

    public Goods(Long id, Category category, String name, String summary, Long price, Long discountPrice, Integer discountPercent, SaleStatus saleStatus, UploadFile photo) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.summary = summary;
        this.price = price;
        this.discountPrice = discountPrice;
        this.discountPercent = discountPercent;
        this.saleStatus = saleStatus;
        this.photo = photo;
    }


    public void saveDetail(GoodsDetail detail) {
        this.goodsDetail = detail;
    }

    public void saveOptions(List<GoodsOption> options) {
        this.goodsOption = new LinkedHashSet<>(options);
    }

    public void edit(Category category, String name, String summary, Long price,
                     Long discountPrice, Integer discountPercent, SaleStatus saleStatus) {
        this.category = category;
        this.name = name;
        this.summary = summary;
        this.price = price;
        this.discountPrice = discountPrice;
        this.discountPercent = discountPercent;
        this.saleStatus = saleStatus;
    }
}
