package com.ddangme.dm.controller.member.api;

import com.ddangme.dm.dto.cash.CashListResponse;
import com.ddangme.dm.dto.member.MemberPrincipal;
import com.ddangme.dm.service.address.AddressService;
import com.ddangme.dm.service.cash.CashService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/my-page")
public class MyPageApiController {

    private final AddressService addressService;
    private final CashService cashService;

    @DeleteMapping("/address/{addressId}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long addressId, @AuthenticationPrincipal MemberPrincipal principal) {
        log.info("addressId={}", addressId);

        addressService.deleteAddress(addressId, principal.getId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/cash/remain")
    public ResponseEntity<Long> remainCash(@AuthenticationPrincipal MemberPrincipal principal) {
        return ResponseEntity.ok(cashService.findRemainCash(principal.getId()));
    }

    @GetMapping("/cash/list")
    public ResponseEntity<List<CashListResponse>> cashList(@AuthenticationPrincipal MemberPrincipal principal) {
        return ResponseEntity.ok(cashService.findCashListByMember(principal.getId()));
    }

    @PostMapping("/cash/charging")
    public ResponseEntity<Void> chargingCash(@AuthenticationPrincipal MemberPrincipal principal,
                                             Long amount) {
        cashService.cashCharging(principal.getId(), amount);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/cash/cancel/{cashId}")
    public ResponseEntity<Void> cancel(@AuthenticationPrincipal MemberPrincipal principal
    , @PathVariable Long cashId) {
        cashService.cancel(principal.getId(), cashId);
        return ResponseEntity.ok().build();
    }
}
