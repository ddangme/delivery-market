package com.ddangme.dm.repository.cart;


import com.ddangme.dm.dto.cart.CartChangeCountProjection;
import com.ddangme.dm.dto.cart.CartListProjection;

import java.util.List;

public interface CartRepositoryCustom {

    List<CartListProjection> findByMemberId(Long memberId);

    CartChangeCountProjection findByOptionPriceInCart(Long cartId);

}
