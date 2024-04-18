package com.ddangme.dm.controller.member;

import com.ddangme.dm.dto.member.MemberPrincipal;
import com.ddangme.dm.exception.DMException;
import com.ddangme.dm.exception.ErrorCode;
import com.ddangme.dm.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;


    @GetMapping("/sign-up")
    public String signUp(Model model) {
        model.addAttribute("member", new SignUpRequest());

        return "member/sign-up";
    }

    @PostMapping("/sign-up")
    public String signUp(@Valid @ModelAttribute("member") SignUpRequest signUpRequest, BindingResult bindingResult) {
        if (memberService.searchMember(signUpRequest.getLoginId()).isPresent()) {
            bindingResult.rejectValue("loginId", "Duplicate");
        }

        if (!signUpRequest.getPassword().equals(signUpRequest.getPasswordCheck())) {
            bindingResult.rejectValue("password", "NotSame");
        }

        if (bindingResult.hasErrors()) {
            log.error(bindingResult.toString());

            return "/member/sign-up";
        }

        memberService.signUp(signUpRequest);
        return "main";
    }

    @GetMapping("/member/find/id")
    public String findId(Model model) {
        model.addAttribute("member", new MemberFindRequest());

        return "member/find-id";
    }

    @GetMapping("/member/find/password")
    public String findPassword(Model model) {
        model.addAttribute("member", new MemberFindRequest());

        return "member/find-pw";
    }

    @GetMapping("/member/info/modify")
    public String modifyInfo(Model model, @AuthenticationPrincipal MemberPrincipal principal) {
        log.info("principal={}", principal);
        ModifyMemberRequest modifyMemberRequest = memberService.searchMember(principal.getLoginId())
                .map(ModifyMemberRequest::fromDTO)
                .orElseThrow(() -> new DMException(ErrorCode.NOT_FOUND_MEMBER));
        model.addAttribute("member", modifyMemberRequest);
        return "member/modify-info";
    }

}
