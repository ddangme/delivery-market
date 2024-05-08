package com.ddangme.dmadmin.dto.goods.response;

import com.ddangme.dmadmin.dto.category.CategoryResponse;
import com.ddangme.dmadmin.model.constants.SaleStatus;
import com.ddangme.dmadmin.model.goods.Goods;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.Comparator;
import java.util.List;

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
    private List<GoodsOptionResponse> goodsOptions;


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
                        .sorted(Comparator.comparing(GoodsOptionResponse::getId))
                        .toList());
    }

}
