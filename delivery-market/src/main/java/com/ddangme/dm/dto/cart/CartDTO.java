package com.ddangme.dm.dto.cart;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class CartDTO {
    private Long id;
    private Long optionId;
    private String optionName;
    private String goodName;
    private Integer count;
    private String photo;
    private Long price;
    private Long discountPrice;
    private boolean checkStatus;


//    public static CartDTO fromProjection(CartListProjection projection, byte[] photo) {
//        return new CartDTO(
//                projection.getId(),
//                projection.getOptionId(),
//                projection.getOptionName(),
//                projection.getGoodName(),
//                projection.getCount(),
//                photo,
//                projection.getPrice(),
//                projection.getDiscountPrice(),
//                projection.getCheckStatus()
//        );
//    }

    public static CartDTO fromProjection(CartListProjection projection) {
        return new CartDTO(
                projection.getId(),
                projection.getOptionId(),
                projection.getOptionName(),
                projection.getGoodName(),
                projection.getCount(),
                projection.getPhoto(),
                projection.getPrice(),
                projection.getDiscountPrice(),
                projection.getCheckStatus()
        );
    }

}
