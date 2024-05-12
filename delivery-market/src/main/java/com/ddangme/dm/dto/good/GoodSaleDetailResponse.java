package com.ddangme.dm.dto.good;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@ToString
@Data
public class GoodSaleDetailResponse {

    private Long id;
    private String name;
    private String summary;
    private Long price;
    private Long discountPrice;
    private Integer discountPercent;
    private String saleStatus;
    private String photo;
    private GoodDetail goodDetail;
    private List<GoodOption> goodOptions;


    @ToString
    @Data
    static class GoodDetail {
        private Long id;
        private String origin;
        private String packagingType;
        private String weightVolume;
        private String allergyInfo;
        private String guidelines;
        private String expiryDate;
        private String description;
    }

    @ToString
    @Data
    static class GoodOption {

        private Long id;
        private String name;
        private Long price;
        private Long discountPrice;
        private Integer discountPercent;
        private Long amount;
        private String saleStatus;
    }
}
