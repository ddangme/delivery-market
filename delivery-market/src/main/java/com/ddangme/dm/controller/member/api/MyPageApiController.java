package com.ddangme.dm.controller.member.api;

import com.ddangme.dm.dto.member.MemberPrincipal;
import com.ddangme.dm.service.address.AddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/my-page")
public class MemberInfoApiController {

    private final AddressService addressService;

    @DeleteMapping("/address/{addressId}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long addressId, @AuthenticationPrincipal MemberPrincipal principal) {
        log.info("addressId={}", addressId);

        addressService.deleteAddress(addressId, principal.getId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/address")
    public ResponseEntity<String> getMainAddress(@AuthenticationPrincipal MemberPrincipal principal) {
        return ResponseEntity.ok(addressService.getMainAddress(principal.getId()));
    }
}
