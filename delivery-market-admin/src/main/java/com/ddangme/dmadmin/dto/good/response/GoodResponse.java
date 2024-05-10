package com.ddangme.dmadmin.dto.good.response;

import com.ddangme.dmadmin.dto.category.CategoryResponse;
import com.ddangme.dmadmin.model.constants.SaleStatus;
import com.ddangme.dmadmin.model.good.Good;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.Comparator;
import java.util.List;

@ToString
@Data
@AllArgsConstructor
public class GoodResponse {

    private Long id;
    private String name;
    private String summary;
    private CategoryResponse category;

    private Long price;
    private Long discountPrice;
    private Integer discountPercent;
    private SaleStatus saleStatus;
    private String photo;

    private GoodDetailResponse goodsDetail;
    private List<GoodOptionResponse> goodsOptions;

    @QueryProjection
    public GoodResponse(Long id, String name, String summary, Long price, Long discountPrice, Integer discountPercent, SaleStatus saleStatus, String photo) {
        this.id = id;
        this.name = name;
        this.summary = summary;
        this.price = price;
        this.discountPrice = discountPrice;
        this.discountPercent = discountPercent;
        this.saleStatus = saleStatus;
        this.photo = photo;
    }

    public static GoodResponse fromEntity(Good entity) {
        return new GoodResponse(
                entity.getId(),
                entity.getName(),
                entity.getSummary(),
                CategoryResponse.fromEntity(entity.getCategory()),
                entity.getPrice(),
                entity.getDiscountPrice(),
                entity.getDiscountPercent(),
                entity.getSaleStatus(),
                entity.getPhoto().getStoreFileName(),

                GoodDetailResponse.fromEntity(entity.getGoodDetail()),
                entity.getGoodOption().stream()
                        .map(GoodOptionResponse::fromEntity)
                        .sorted(Comparator.comparing(GoodOptionResponse::getId))
                        .toList());
    }

}
