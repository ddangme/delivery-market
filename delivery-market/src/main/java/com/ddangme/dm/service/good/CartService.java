package com.ddangme.dm.service.good;

import com.ddangme.dm.dto.cart.CartListProjection;
import com.ddangme.dm.dto.cart.request.CartChangeCheckRequest;
import com.ddangme.dm.dto.cart.request.CartChangeCountRequest;
import com.ddangme.dm.dto.cart.request.CartRequest;
import com.ddangme.dm.dto.cart.response.CartChangeCountResponse;
import com.ddangme.dm.dto.cart.response.CartListResponse;
import com.ddangme.dm.dto.order.OrderCartProjection;
import com.ddangme.dm.exception.DMException;
import com.ddangme.dm.exception.ErrorCode;
import com.ddangme.dm.model.good.Cart;
import com.ddangme.dm.model.good.GoodOption;
import com.ddangme.dm.model.member.Member;
import com.ddangme.dm.repository.member.MemberRepository;
import com.ddangme.dm.repository.cart.CartRepository;
import com.ddangme.dm.repository.good.GoodOptionRepository;
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
        if (requests == null || requests.isEmpty()) {
            throw new DMException(ErrorCode.NOT_CHOICE_OPTION);
        }
        Member member = findMember(memberId);
        boolean existedCart = saveCartOrAddCount(memberId, requests, member);

        return getResultMessage(existedCart, requests.size());
    }

    private boolean saveCartOrAddCount(Long memberId, List<CartRequest> requests, Member member) {
        boolean existedCart = false;

        for (CartRequest request : requests) {
            Optional<Cart> cart = findCart(memberId, request.getOptionId());
            if (cart.isPresent()) {
                cart.get().addQuantity(request.getQuantity());
                existedCart = true;
            } else {
                Cart newCart = Cart.create(member, findOption(request.getOptionId()), request.getQuantity());
                cartRepository.save(newCart);
            }
        }
        return existedCart;
    }

    private String getResultMessage(boolean existedCart, int quantity) {
        String message = "장바구니에 " + quantity + " 개의 상품이 추가되었습니다.";
        if (existedCart) {
            message += "\n이미 담은 상품의 수량을 추가했습니다.";
        }
        return message;
    }

    public Integer getCartCount(Long memberId) {
        return cartRepository.countByMemberId(memberId);
    }

    public CartListResponse findCartByPackagingType(Long memberId) {
        List<CartListProjection> projections = cartRepository.findByMemberId(memberId);

        CartListResponse response = new CartListResponse();
        for (CartListProjection projection : projections) {
            projection.calculateTotalPrice();
            response.add(projection);
        }

        return response;
    }

    public List<OrderCartProjection> findCarts(Long memberId) {
        findMember(memberId);
        return cartRepository.findByMemberIdAtOrder(memberId);
    }

    @Transactional
    public void deleteCart(Long memberId, List<Long> cartIds) {
        findMember(memberId);

        if (cartIds.isEmpty()) {
            throw new DMException(ErrorCode.NOT_CHOICE_CART);
        }

        for (Long cartId : cartIds) {
            Cart cart = cartRepository.findById(cartId)
                    .orElseThrow(() -> new DMException(ErrorCode.NOT_FOUND_CART));
            cartRepository.delete(cart);
        }
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

    @Transactional
    public CartChangeCountResponse changeCartCount(Long memberId, CartChangeCountRequest request) {
        findMember(memberId);
        Cart cart = cartRepository.findById(request.getId())
                .orElseThrow(() -> new DMException(ErrorCode.NOT_FOUND_CART));

        cart.changeCount(request.getQuantity());
        return CartChangeCountResponse.of(
                cartRepository.findByOptionPriceInCart(request.getId()), request.getQuantity());
    }

    @Transactional
    public void changeCartCheckStatus(Long memberId, CartChangeCheckRequest request) {
        findMember(memberId);
        Cart cart = cartRepository.findById(request.getId())
                .orElseThrow(() -> new DMException(ErrorCode.NOT_FOUND_CART));

        cart.changeCheckStatus(request.getCheckStatus());
    }


    @Transactional
    public void changeAllCartCheckStatus(Long memberId, Boolean checkStatus) {
        findMember(memberId);
        cartRepository.findIdAndCountByMemberId(memberId)
                .forEach(cart -> cart.changeCheckStatus(checkStatus));
    }

}
