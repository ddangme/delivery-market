package com.ddangme.dmadmin.controller.member;

import com.ddangme.dmadmin.dto.cash.CashListProjection;
import com.ddangme.dmadmin.service.member.CashManageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberManageApiController {

    private final CashManageService cashManageService;

    @GetMapping("/cash/list")
    public ResponseEntity<Page<CashListProjection>> getCashList(@PageableDefault(size = 2) Pageable pageable) {
        return ResponseEntity.ok(cashManageService.search(pageable));
    }
}
