package com.ddangme.dm.repository.member;

import com.ddangme.dm.dto.order.OrderAddressProjection;

import java.util.List;

public interface MemberRepositoryCustom {

    OrderAddressProjection findMainAddressByMemberId(Long memberId);

    List<OrderAddressProjection> findAddressListByMemberId(Long memberId);
}
