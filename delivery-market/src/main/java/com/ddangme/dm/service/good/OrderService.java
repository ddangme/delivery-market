package com.ddangme.dm.service.good;

import com.ddangme.dm.dto.address.CartValidateProjection;
import com.ddangme.dm.dto.order.request.OrderRequest;
import com.ddangme.dm.exception.DMException;
import com.ddangme.dm.exception.ErrorCode;
import com.ddangme.dm.model.good.GoodOption;
import com.ddangme.dm.model.member.Member;
import com.ddangme.dm.repository.AddressRepository;
import com.ddangme.dm.repository.cart.CartRepository;
import com.ddangme.dm.repository.good.GoodOptionRepository;
import com.ddangme.dm.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final AddressRepository addressRepository;
    private final GoodOptionRepository optionRepository;

    public void validateForOrder(Long memberId) {
        Member member = findMember(memberId);
        Long totalPrice = 0L;

        List<CartValidateProjection> projections = cartRepository.findForValidateByMemberId(memberId);
        if (projections.isEmpty()) {
            throw new DMException(ErrorCode.NOT_CHOICE_CART);
        }
        for (CartValidateProjection projection : projections) {
            if (projection.getBuyQuantity() > projection.getRemainQuantity()) {
                throw new DMException(ErrorCode.EXIST_NON_ORDER_OPTION);
            }

            if (projection.getDiscountPrice() == null || projection.getDiscountPrice() == 0) {
                totalPrice += projection.getPrice();
            } else {
                totalPrice += projection.getDiscountPrice();
            }
        }

        member.canBuy(totalPrice);
    }

    private Member findMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new DMException(ErrorCode.NOT_FOUND_ACCOUNT));
    }

    public void order(OrderRequest request, Long memberId) {
        validateForOrder(memberId);
        List<Long> buyOptionIds = request.getOptionIds();

    }
}
