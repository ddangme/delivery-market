package com.ddangme.dmadmin.dto.good.request;


import com.ddangme.dmadmin.model.constants.SaleStatus;
import com.ddangme.dmadmin.model.constants.UploadFile;
import com.ddangme.dmadmin.model.good.Category;
import com.ddangme.dmadmin.model.good.Good;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@ToString
@Data
public class GoodRequest {

    private Long id;
    private String name;
    private String summary;
    private Long categoryId;
    private Long price;
    private Long discountPrice;
    private Integer discountPercent;
    private SaleStatus saleStatus;

    private GoodDetailRequest goodsDetail;
    private List<GoodOptionRequest> goodsOptions;

    public Good toEntity(Category category, UploadFile photo) {
        calculateDiscount();

        Good good = new Good(
                category,
                name,
                summary,
                price,
                discountPrice,
                discountPercent,
                saleStatus,
                photo
        );

        good.saveDetail(goodsDetail.toEntity(good));
        good.saveOptions(goodsOptions.stream()
                .map(option -> option.toEntity(good))
                .toList());

        return good;
    }

    private void calculateDiscount() {
        if (discountPrice == null && discountPercent != null) {
            long discount = price * (discountPercent / 100);
            discountPrice = price - discount;
        } else if (discountPercent == null && discountPercent != null) {
            long discount = price - discountPrice;
            discountPercent = Math.toIntExact((discount / price) * 100);
        }
    }
}


