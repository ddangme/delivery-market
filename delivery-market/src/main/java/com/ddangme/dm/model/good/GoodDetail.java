package com.ddangme.dm.model.good;

import com.ddangme.dm.model.constants.PackagingType;
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

    private String origin;

    @Enumerated(EnumType.STRING)
    private PackagingType packagingType;

    private String weightVolume;

    private String allergyInfo;

    private String guidelines;

    private String expiryDate;

    private String description;

}
