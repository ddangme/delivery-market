package com.ddangme.dm.service.order;

import com.ddangme.dm.dto.address.CartValidateProjection;
import com.ddangme.dm.dto.order.request.OrderRequest;
import com.ddangme.dm.exception.DMException;
import com.ddangme.dm.exception.ErrorCode;
import com.ddangme.dm.model.Address;
import com.ddangme.dm.model.good.Cart;
import com.ddangme.dm.model.member.Member;
import com.ddangme.dm.model.order.Order;
import com.ddangme.dm.model.order.OrderAddress;
import com.ddangme.dm.model.order.OrderGood;
import com.ddangme.dm.repository.AddressRepository;
import com.ddangme.dm.repository.cart.CartRepository;
import com.ddangme.dm.repository.member.MemberRepository;
import com.ddangme.dm.repository.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final AddressRepository addressRepository;
    private final OrderRepository orderRepository;

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

    @Transactional
    public void cancelOrder(Long memberId, Long orderId) {
        findMember(memberId);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new DMException(ErrorCode.NOT_EXIST_ORDER));

        order.cancelOrder();
    }

    private Member findMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new DMException(ErrorCode.NOT_FOUND_ACCOUNT));
    }

    @Transactional
    public void order(OrderRequest request, Long memberId) {
        Member member = findMember(memberId);
        validateForOrder(memberId);

        OrderAddress orderAddress = getOrderAddress(request.getAddressId());
        List<OrderGood> orderGoods = getOrderGoods(request.getCartIds());
        Order order = new Order(member, orderAddress, orderGoods);

        orderRepository.save(order);
    }

    private OrderAddress getOrderAddress(Long addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new DMException(ErrorCode.NOT_FOUND_ADDRESS));

        return new OrderAddress(
                address.getRecipientName(),
                address.getRecipientPhone(),
                address.getRoad(),
                address.getDetail(),
                address.getZipcode()
        );
    }

    private List<OrderGood> getOrderGoods(List<Long> cartIds) {
        List<OrderGood> orderGoods = new ArrayList<>();

        for (Long cartId : cartIds) {
            Cart cart = cartRepository.findById(cartId)
                    .orElseThrow(() -> new DMException(ErrorCode.NOT_FOUND_CART));
            cartRepository.delete(cart);
            cart.getOption().minusQuantity(cart.getQuantity());
            orderGoods.add(new OrderGood(cart.getOption(), cart.getQuantity()));
        }

        return orderGoods;
    }
}
