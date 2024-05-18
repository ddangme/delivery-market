package com.ddangme.dm.controller.good.api;

import com.ddangme.dm.dto.member.MemberPrincipal;
import com.ddangme.dm.dto.pick.PickedGoodResponse;
import com.ddangme.dm.service.good.PickService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PickApiController {

    private final PickService pickService;

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
}
