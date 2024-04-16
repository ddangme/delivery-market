package com.ddangme.dm.controller.member;

import com.ddangme.dm.exception.DMException;
import com.ddangme.dm.exception.ErrorCode;
import com.ddangme.dm.model.member.Member;
import com.ddangme.dm.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
            throw new DMException(ErrorCode.DUPLICATED_LOGIN_ID);
        }
        if (bindingResult.hasErrors()) {
            log.error(bindingResult.toString());

            return "/member/sign-up";
        }

        return "main";
    }

    @PostMapping("/api/id-duplicate-check")
    public ResponseEntity<Void> idDuplicateCheck(@RequestBody String loginId) {
        if (memberService.searchMember(loginId).isPresent()) {
            throw new DMException(ErrorCode.DUPLICATED_LOGIN_ID);
        }

        return ResponseEntity.ok().build();
    }

}
