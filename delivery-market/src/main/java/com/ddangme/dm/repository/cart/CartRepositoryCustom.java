package com.ddangme.dm.repository.cart;

import com.ddangme.dm.dto.good.CartProjection;

import java.util.List;

public interface CartRepositoryCustom {

    List<CartProjection> findByMemberId(Long memberId);

}
