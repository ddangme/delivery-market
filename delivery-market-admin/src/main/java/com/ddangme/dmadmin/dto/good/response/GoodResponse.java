package com.ddangme.dmadmin.dto.good.response;

import com.ddangme.dmadmin.dto.category.CategoryResponse;
import com.ddangme.dmadmin.model.constants.SaleStatus;
import com.ddangme.dmadmin.model.good.Good;
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
