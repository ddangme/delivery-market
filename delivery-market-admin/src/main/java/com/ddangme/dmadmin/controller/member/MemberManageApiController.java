package com.ddangme.dmadmin.controller.member;

import com.ddangme.dmadmin.dto.admin.AdminPrincipal;
import com.ddangme.dmadmin.dto.cash.CashChangeRequest;
import com.ddangme.dmadmin.dto.cash.CashListProjection;
import com.ddangme.dmadmin.dto.member.MemberListResponse;
import com.ddangme.dmadmin.service.member.CashManageService;
import com.ddangme.dmadmin.service.member.MemberManageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberManageApiController {

    private final CashManageService cashService;
    private final MemberManageService memberService;

    @GetMapping("/cash/list")
    public ResponseEntity<Page<CashListProjection>> getCashList(Pageable pageable) {
        return ResponseEntity.ok(cashService.search(pageable));
    }

    @PostMapping("/cash/status-change")
    public ResponseEntity<Void> changeStatus(@RequestBody CashChangeRequest request,
                                             @AuthenticationPrincipal AdminPrincipal principal) {
        log.info("request={}", request);
        cashService.changeStatus(principal.getId(), request);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Page<MemberListResponse>> findMembers(Pageable pageable) {
        Page<MemberListResponse> member = memberService.findMember(pageable);
        log.info("member={}", member);
        return ResponseEntity.ok(member);

    }
}
