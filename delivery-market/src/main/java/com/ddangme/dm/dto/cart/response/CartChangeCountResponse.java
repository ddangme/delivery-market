package com.ddangme.dm.dto.cart.response;

import com.ddangme.dm.dto.cart.CartChangeCountProjection;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartChangeCountResponse {

    private Long cartId;
    private Integer quantity;
    private Long price;
    private Long discountPrice;

    public static CartChangeCountResponse of(CartChangeCountProjection projection, Integer quantity) {
        Long totalPrice = calculateTotal(projection.getPrice(), quantity);
        Long totalDiscountPrice = calculateTotal(projection.getDiscountPrice(), quantity);

        return new CartChangeCountResponse(projection.getCartId(), quantity, totalPrice, totalDiscountPrice);
    }

    private static Long calculateTotal(Long price, Integer count) {
        if (price == null) {
            return null;
        }

        return price * count;
    }

}
