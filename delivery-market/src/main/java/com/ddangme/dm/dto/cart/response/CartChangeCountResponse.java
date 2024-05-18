package com.ddangme.dm.dto.cart.response;

import com.ddangme.dm.dto.cart.CartChangeCountProjection;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartChangeCountResponse {

    private Long cartId;
    private Long price;
    private Long discountPrice;

    public static CartChangeCountResponse of(CartChangeCountProjection projection, Integer count) {
        Long totalPrice = calculateTotal(projection.getPrice(), count);
        Long totalDiscountPrice = calculateTotal(projection.getDiscountPrice(), count);

        return new CartChangeCountResponse(projection.getCartId(), totalPrice, totalDiscountPrice);
    }

    private static Long calculateTotal(Long price, Integer count) {
        if (price == null) {
            return null;
        }

        return price * count;
    }

}
