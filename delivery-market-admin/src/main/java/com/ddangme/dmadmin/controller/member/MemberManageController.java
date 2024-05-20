package com.ddangme.dmadmin.controller.member;

import com.ddangme.dmadmin.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberManageController {

    private final MemberRepository memberRepository;

    @GetMapping("/cash/list")
    public String cashList() {
        return "/member/cash-list";
    }

    @GetMapping
    public String memberList() {
        return "/member/member-list";
    }

    @GetMapping("/{memberId}")
    public String memberDetail() {
        return "/member/member-detail";
    }
}
