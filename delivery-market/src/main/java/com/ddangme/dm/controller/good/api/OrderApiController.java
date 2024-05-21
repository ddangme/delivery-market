package com.ddangme.dm.controller.good.api;

import com.ddangme.dm.dto.member.MemberPrincipal;
import com.ddangme.dm.dto.order.OrderAddressProjection;
import com.ddangme.dm.dto.order.OrderAddressResponse;
import com.ddangme.dm.dto.order.OrderCartProjection;
import com.ddangme.dm.dto.order.OrderResponse;
import com.ddangme.dm.service.good.CartService;
import com.ddangme.dm.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.Order;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderApiController {

    private final CartService cartService;
    private final MemberService memberService;

    @GetMapping("/address/list")
    public ResponseEntity<List<OrderAddressResponse>> addressList(@AuthenticationPrincipal MemberPrincipal principal) {
        return ResponseEntity.ok(memberService.findAddressListByMemberId(principal.getId()));
    }

    @GetMapping("/info")
    public ResponseEntity<OrderResponse> orderInfo(@AuthenticationPrincipal MemberPrincipal principal) {
        OrderResponse response = memberService.findOrderInfo(principal.getId());
        response.setGood(cartService.findCarts(principal.getId()));

        return ResponseEntity.ok(response);
    }

}
