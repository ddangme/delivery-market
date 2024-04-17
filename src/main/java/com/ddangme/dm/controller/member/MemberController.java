package com.ddangme.dm.controller.member;

import com.ddangme.dm.dto.member.MemberDTO;
import com.ddangme.dm.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Optional;

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

        memberService.signUpMember(signUpRequest);
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

}
