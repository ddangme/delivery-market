package com.ddangme.dmadmin.model.good;

import com.ddangme.dmadmin.model.constants.PackagingType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class GoodDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "good_id")
    private Good good;

    private String origin;

    @Enumerated(EnumType.STRING)
    private PackagingType packagingType;

    private String weightVolume;

    private String allergyInfo;

    private String guidelines;

    private String expiryDate;

    private String description;

    public GoodDetail(Good good, String origin, PackagingType packagingType, String weightVolume, String allergyInfo, String guidelines, String expiryDate, String description) {
        this.good = good;
        this.origin = origin;
        this.packagingType = packagingType;
        this.weightVolume = weightVolume;
        this.allergyInfo = allergyInfo;
        this.guidelines = guidelines;
        this.expiryDate = expiryDate;
        this.description = description;
    }

    public void edit(String origin, PackagingType packagingType, String weightVolume, String allergyInfo, String guidelines, String expiryDate, String description) {
        this.origin = origin;
        this.packagingType = packagingType;
        this.weightVolume = weightVolume;
        this.allergyInfo = allergyInfo;
        this.guidelines = guidelines;
        this.expiryDate = expiryDate;
        this.description = description;
    }
}
