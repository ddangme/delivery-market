package com.ddangme.dm.service.order;

import com.ddangme.dm.constants.OrderHistory;
import com.ddangme.dm.dto.order.response.OrderDetailResponse;
import com.ddangme.dm.dto.order.response.OrderListResponse;
import com.ddangme.dm.exception.DMException;
import com.ddangme.dm.exception.ErrorCode;
import com.ddangme.dm.model.member.Member;
import com.ddangme.dm.model.order.Order;
import com.ddangme.dm.repository.member.MemberRepository;
import com.ddangme.dm.repository.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class OrderSearchService {

    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;

    public List<OrderListResponse> getOrderList(Long memberId, OrderHistory orderHistory) {
        findMember(memberId);
        return orderRepository.findOrdersByStartDateTimeAndMemberId(
                        orderHistory.getStartDateTime(),
                        memberId).stream()
                .map(OrderListResponse::fromEntity)
                .toList();
    }

    public OrderDetailResponse getOrderDetailResponse(Long memberId, Long orderId) {
        Member member = findMember(memberId);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new DMException(ErrorCode.NOT_EXIST_ORDER));

        if (!order.getMember().equals(member)) {
            throw new DMException(ErrorCode.UN_AUTHORIZED_ACCESS);
        }
        return new OrderDetailResponse(order);
    }

    private Member findMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new DMException(ErrorCode.NOT_FOUND_ACCOUNT));
    }

}
