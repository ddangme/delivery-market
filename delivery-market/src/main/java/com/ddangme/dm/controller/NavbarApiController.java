package com.ddangme.dm.controller;

import com.ddangme.dm.dto.member.MemberPrincipal;
import com.ddangme.dm.service.good.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/navbar")
public class NavbarApiController {

    private final CartService cartService;

    @GetMapping("/login-check")
    public String loginCheck() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return null;
        }

        MemberPrincipal principal = (MemberPrincipal) authentication.getPrincipal();

        return principal.getName();
    }

    @GetMapping("/cart-count")
    public Integer cartCount(@AuthenticationPrincipal MemberPrincipal principal) {
        return cartService.getCartCount(principal.getId());
    }
}
