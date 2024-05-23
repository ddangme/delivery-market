package com.ddangme.dm.dto.cart;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class CartDTO {
    private Long id;
    private Long goodId;
    private Long optionId;
    private String optionName;
    private String goodName;
    private Integer quantity;
    private Integer remainQuantity;
    private String photo;
    private Long price;
    private Long discountPrice;
    private boolean checkStatus;

    public static CartDTO fromProjection(CartListProjection projection) {
        return new CartDTO(
                projection.getId(),
                projection.getGoodId(),
                projection.getOptionId(),
                projection.getOptionName(),
                projection.getGoodName(),
                projection.getQuantity(),
                projection.getRemainQuantity(),
                projection.getPhoto(),
                projection.getPrice(),
                projection.getDiscountPrice(),
                projection.getCheckStatus()
        );
    }

}
