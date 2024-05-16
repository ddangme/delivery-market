package com.ddangme.dm.controller.member;

import com.ddangme.dm.service.good.PickService;
import com.ddangme.dm.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/my-page")
public class MemberGoodController {

    private final MemberService memberService;
    private final PickService pickService;

    @GetMapping("/pick/list")
    public String pickList() {
        return "/member/my-page/pick-list";
    }

}
