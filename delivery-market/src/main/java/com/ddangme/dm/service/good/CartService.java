package com.ddangme.dm.service.good;

import com.ddangme.dm.dto.good.request.CartRequest;
import com.ddangme.dm.dto.good.request.OptionRequest;
import com.ddangme.dm.exception.DMException;
import com.ddangme.dm.exception.ErrorCode;
import com.ddangme.dm.model.good.Cart;
import com.ddangme.dm.model.good.Good;
import com.ddangme.dm.model.good.GoodOption;
import com.ddangme.dm.model.member.Member;
import com.ddangme.dm.repository.MemberRepository;
import com.ddangme.dm.repository.good.CartRepository;
import com.ddangme.dm.repository.good.GoodOptionRepository;
import com.ddangme.dm.repository.good.GoodRepository;
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
public class CartService {

    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final GoodOptionRepository optionRepository;

    @Transactional
    public String save(Long memberId, List<CartRequest> requests) {
        Member member = findMember(memberId);
        boolean existedCart = saveCartOrAddCount(memberId, requests, member);

        return getResultMessage(existedCart, requests.size());
    }

    private boolean saveCartOrAddCount(Long memberId, List<CartRequest> requests, Member member) {
        boolean existedCart = false;

        for (CartRequest request : requests) {
            Optional<Cart> cart = findCart(memberId, request.getOptionId());
            if (cart.isPresent()) {
                cart.get().addCount(request.getCount());
                existedCart = true;
            } else {
                Cart newCart = Cart.create(member, findOption(request.getOptionId()), request.getCount());
                cartRepository.save(newCart);
            }
        }
        return existedCart;
    }

    private String getResultMessage(boolean existedCart, int count) {
        String message = "장바구니에 " + count + " 개의 상품이 추가되었습니다.";
        if (existedCart) {
            message += "\n이미 담은 상품의 수량을 추가했습니다.";
        }
        return message;
    }

    private Optional<Cart> findCart(Long memberId, Long optionId) {
        return cartRepository.findByMemberIdAndOptionId(memberId, optionId);
    }

    private GoodOption findOption(Long optionId) {
        return optionRepository.findById(optionId)
                .orElseThrow(() -> new DMException(ErrorCode.NOT_FOUND_OPTION));
    }

    private Member findMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new DMException(ErrorCode.NOT_FOUND_ACCOUNT));
    }
}
