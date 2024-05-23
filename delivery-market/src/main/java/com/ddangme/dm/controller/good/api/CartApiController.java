package com.ddangme.dm.controller.good.api;


import com.ddangme.dm.dto.cart.request.CartChangeCheckRequest;
import com.ddangme.dm.dto.cart.request.CartChangeCountRequest;
import com.ddangme.dm.dto.cart.request.CartRequest;
import com.ddangme.dm.dto.cart.response.CartChangeCountResponse;
import com.ddangme.dm.dto.cart.response.CartListResponse;
import com.ddangme.dm.dto.cart.response.CartResponse;
import com.ddangme.dm.dto.member.MemberPrincipal;
import com.ddangme.dm.service.good.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartApiController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<CartResponse> cartSave(@RequestBody List<CartRequest> requests,
                                                 @AuthenticationPrincipal MemberPrincipal principal) {
        log.info("requests={}", requests);
        String message = cartService.save(principal.getId(), requests);
        Integer count = cartService.getCartCount(principal.getId());
        return ResponseEntity.ok(new CartResponse(count, message));
    }

    @GetMapping("/list")
    public ResponseEntity<CartListResponse> findCartByPackagingType(@AuthenticationPrincipal MemberPrincipal principal) {
        CartListResponse carts = cartService.findCartByPackagingType(principal.getId());

        return ResponseEntity.ok(carts);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCart(@AuthenticationPrincipal MemberPrincipal principal
            , @RequestBody List<Long> cartIds) {
        log.info("cartIds={}", cartIds);
        cartService.deleteCart(principal.getId(), cartIds);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/change/count")
    public ResponseEntity<CartChangeCountResponse> changeOptionQuantity(@AuthenticationPrincipal MemberPrincipal principal,
                                                                        @RequestBody CartChangeCountRequest request) {
        return ResponseEntity.ok(cartService.changeCartCount(principal.getId(), request));
    }

    @PostMapping("/change/check-status")
    public ResponseEntity<Void> changeCartStatus(@AuthenticationPrincipal MemberPrincipal principal,
                                                 @RequestBody CartChangeCheckRequest request) {
        cartService.changeCartCheckStatus(principal.getId(), request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/change/all-check-status")
    public ResponseEntity<Void> changeAllCartCount(@AuthenticationPrincipal MemberPrincipal principal,
                                                   @RequestBody Boolean checkStatus) {
        cartService.changeAllCartCheckStatus(principal.getId(), checkStatus);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/validate")
    public ResponseEntity<Void> validateBeforeOrder(@AuthenticationPrincipal MemberPrincipal principal) {
        cartService.validateForOrder(principal.getId());

        return ResponseEntity.ok().build();
    }
}
