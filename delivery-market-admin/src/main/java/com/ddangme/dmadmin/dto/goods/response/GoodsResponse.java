package com.ddangme.dmadmin.dto.goods.response;

import com.ddangme.dmadmin.dto.category.CategoryResponse;
import com.ddangme.dmadmin.model.constants.SaleStatus;
import com.ddangme.dmadmin.model.goods.Goods;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.Set;
import java.util.stream.Collectors;

@ToString
@Data
@AllArgsConstructor
public class GoodsResponse {

    private Long id;
    private String name;
    private String summary;
    private CategoryResponse category;

    private Long price;
    private Long discountPrice;
    private Integer discountPercent;
    private SaleStatus saleStatus;
    private String photo;

    private GoodsDetailResponse goodsDetail;
    private Set<GoodsOptionResponse> goodsOptions;


    public static GoodsResponse fromEntity(Goods entity) {
        return new GoodsResponse(
                entity.getId(),
                entity.getName(),
                entity.getSummary(),
                CategoryResponse.fromEntity(entity.getCategory()),
                entity.getPrice(),
                entity.getDiscountPrice(),
                entity.getDiscountPercent(),
                entity.getSaleStatus(),
                entity.getPhoto().getStoreFileName(),

                GoodsDetailResponse.fromEntity(entity.getGoodsDetail()),
                entity.getGoodsOption().stream()
                        .map(GoodsOptionResponse::fromEntity)
                        .collect(Collectors.toUnmodifiableSet())
        );
    }
}
