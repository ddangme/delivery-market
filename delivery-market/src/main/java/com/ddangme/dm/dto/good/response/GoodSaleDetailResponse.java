package com.ddangme.dm.dto.good.response;

import com.ddangme.dm.model.good.Good;
import com.ddangme.dm.model.good.GoodDetail;
import com.ddangme.dm.model.good.GoodOption;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@ToString
@Data
@AllArgsConstructor
public class GoodSaleDetailResponse {

    private Long id;
    private String name;
    private String summary;
    private Long price;
    private Long discountPrice;
    private Integer discountPercent;
    private String saleStatus;
    private String photo;
    private GoodDetailResponse goodDetail;
    private List<GoodOptionResponse> goodOptions;


    @ToString
    @Data
    @AllArgsConstructor
    static class GoodDetailResponse {
        private Long id;
        private String origin;
        private String packagingType;
        private String weightVolume;
        private String allergyInfo;
        private String guidelines;
        private String expiryDate;
        private String description;

        public static GoodDetailResponse fromEntity(GoodDetail entity) {
            return new GoodDetailResponse(
                    entity.getId(),
                    entity.getOrigin(),
                    entity.getPackagingType().getType(),
                    entity.getWeightVolume(),
                    entity.getAllergyInfo(),
                    entity.getGuidelines(),
                    entity.getExpiryDate(),
                    entity.getDescription()
            );
        }
    }

    @ToString
    @Data
    @AllArgsConstructor
    static class GoodOptionResponse {

        private Long id;
        private String name;
        private Long price;
        private Long discountPrice;
        private Integer discountPercent;
        private Integer quantity;
        private String saleStatus;

        public static GoodOptionResponse fromEntity(GoodOption entity) {
            return new GoodOptionResponse(
                    entity.getId(),
                    entity.getName(),
                    entity.getPrice(),
                    entity.getDiscountPrice(),
                    entity.getDiscountPercent(),
                    entity.getQuantity(),
                    entity.getSaleStatus().getStatus()
            );
        }
    }

    public static GoodSaleDetailResponse fromEntity(Good entity) {
        return new GoodSaleDetailResponse(
                entity.getId(),
                entity.getName(),
                entity.getSummary(),
                entity.getPrice(),
                entity.getDiscountPrice(),
                entity.getDiscountPercent(),
                entity.getSaleStatus().getStatus(),
                entity.getPhotoStoreFileName(),
                GoodDetailResponse.fromEntity(entity.getGoodDetail()),
                entity.getGoodOptions()
                        .stream().map(GoodOptionResponse::fromEntity)
                        .toList()
        );
    }

}
