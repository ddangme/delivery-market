package com.ddangme.dmadmin.model.goods;

import com.ddangme.dmadmin.model.AuditingFields;
import com.ddangme.dmadmin.model.constants.PackagingType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "DELETED_AT IS NULL")
public class GoodsDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "goods_detail_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goods_id")
    private Goods goods;

    private String origin;

    @Enumerated(EnumType.STRING)
    private PackagingType packagingType;

    private String weightVolume;

    private String allergyInfo;

    private String guidelines;

    private String expiryDate;

    private String description;

}
