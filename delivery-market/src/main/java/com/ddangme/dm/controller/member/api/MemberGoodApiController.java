package com.ddangme.dm.controller.member.api;


import com.ddangme.dm.dto.cart.request.CartChangeCheckRequest;
import com.ddangme.dm.dto.cart.request.CartChangeCountRequest;
import com.ddangme.dm.dto.cart.request.CartRequest;
import com.ddangme.dm.dto.cart.response.CartListResponse;
import com.ddangme.dm.dto.cart.response.CartResponse;
import com.ddangme.dm.dto.member.MemberPrincipal;
import com.ddangme.dm.dto.pick.PickedGoodResponse;
import com.ddangme.dm.service.FileService;
import com.ddangme.dm.service.good.CartService;
import com.ddangme.dm.service.good.PickService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberGoodApiController {

    private final PickService pickService;
    private final CartService cartService;
    private final FileService fileService;

    @GetMapping("/my-page/pick/list")
    public ResponseEntity<List<PickedGoodResponse>> pickList(@AuthenticationPrincipal MemberPrincipal principal) {
        return ResponseEntity.ok(pickService.findPickedGood(principal.getId()));
    }

    @DeleteMapping("/goods/pick/{goodId}")
    public ResponseEntity<Void> deletePick(@AuthenticationPrincipal MemberPrincipal principal, @PathVariable Long goodId) {
        pickService.deletePick(principal.getId(), goodId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/goods/pick/{goodId}")
    public ResponseEntity<Boolean> pick(@PathVariable Long goodId,
                                        @AuthenticationPrincipal MemberPrincipal principal) {
        return ResponseEntity.ok().body(pickService.pick(goodId, principal.getId()));
    }

    @GetMapping("/goods/find/pick/{goodId}")
    public ResponseEntity<Boolean> findPick(@PathVariable Long goodId,
                                            @AuthenticationPrincipal MemberPrincipal principal) {
        return ResponseEntity.ok().body(pickService.findPick(goodId, principal.getId()));
    }

    @PostMapping("/goods/cart")
    public ResponseEntity<CartResponse> cart(@RequestBody List<CartRequest> requests,
                                             @AuthenticationPrincipal MemberPrincipal principal) {
        log.info("requests={}", requests);
        String message = cartService.save(principal.getId(), requests);
        Integer count = cartService.getCartCount(principal.getId());
        return ResponseEntity.ok(new CartResponse(count, message));
    }

    @GetMapping("/goods/cart/list")
    public ResponseEntity<CartListResponse> findCartList(@AuthenticationPrincipal MemberPrincipal principal) throws IOException {
        return ResponseEntity.ok(cartService.findCartByPackagingType(principal.getId()));
    }

    @DeleteMapping("/goods/cart")
    public ResponseEntity<Void> deleteCart(@AuthenticationPrincipal MemberPrincipal principal
            , @RequestBody List<Long> cartIds) {
        log.info("cartIds={}", cartIds);
        cartService.deleteCart(principal.getId(), cartIds);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/goods/cart/change/count")
    public ResponseEntity<Void> changeCartCount(@AuthenticationPrincipal MemberPrincipal principal,
                                                @RequestBody CartChangeCountRequest request) {
        cartService.changeCartCount(principal.getId(), request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/goods/cart/change/check-status")
    public ResponseEntity<Void> changeCartCount(@AuthenticationPrincipal MemberPrincipal principal,
                                                @RequestBody List<CartChangeCheckRequest> requests) {
        cartService.changeCartCheckStatus(principal.getId(), requests);
        return ResponseEntity.ok().build();
    }
}
