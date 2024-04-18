package com.ddangme.dm.controller.member;

import com.ddangme.dm.dto.member.MemberPrincipal;
import com.ddangme.dm.dto.member.request.MemberFindRequest;
import com.ddangme.dm.dto.member.request.ModifyMemberRequest;
import com.ddangme.dm.dto.member.request.SignUpRequest;
import com.ddangme.dm.exception.DMException;
import com.ddangme.dm.exception.ErrorCode;
import com.ddangme.dm.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder encoder;


    @GetMapping("/sign-up")
    public String signUp(Model model) {
        model.addAttribute("member", new SignUpRequest());

        return "member/sign-up";
    }

    @PostMapping("/sign-up")
    public String signUp(@Valid @ModelAttribute("member") SignUpRequest request, BindingResult bindingResult) {
        if (memberService.findByLoginId(request.getLoginId()).isPresent()) {
            bindingResult.rejectValue("loginId", "Duplicate");
        }

        if (request.notEqualsPasswordCheck()) {
            bindingResult.rejectValue("password", "NotSame");
        }

        if (bindingResult.hasErrors()) {
            log.error(bindingResult.toString());

            return "/member/sign-up";
        }

        memberService.signUp(request);
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

    @GetMapping({"/my-page", "/my-page/info/modify"})
    public String modifyInfo(Model model, @AuthenticationPrincipal MemberPrincipal principal) {
//      TODO:  mypage/인 경우 회원, 적립금, DM 캐시, 쿠폰 수량, 찜한 상품 수량 출력되도록 !
        log.info("principal={}", principal);
        ModifyMemberRequest modifyMemberRequest = memberService.findByLoginId(principal.getLoginId())
                .map(ModifyMemberRequest::fromDTO)
                .orElseThrow(() -> new DMException(ErrorCode.NOT_FOUND_MEMBER));
        model.addAttribute("member", modifyMemberRequest);

        return "member/modify-info";
    }

    @PostMapping("/my-page/info/modify")
    public String modifyInfo(@Valid @ModelAttribute("member") ModifyMemberRequest request, BindingResult bindingResult,
                             @AuthenticationPrincipal MemberPrincipal principal) {
        log.info("request={}", request);
        if (!principal.getLoginId().equals(request.getLoginId())) {
            // TODO: 권한이 없습니다. error 페이지로 이동하기
        }

        if (!encoder.matches(request.getPassword(), principal.getPassword())) {
            bindingResult.rejectValue("password", "NotSame");
        } else if (request.getPassword().equals(request.getNewPassword())) {
            bindingResult.rejectValue("newPassword", "Unable");
        }

        if (request.notEqualsPasswordCheck()) {
            bindingResult.rejectValue("newPassword", "NotSame");
        }



        memberService.findByLoginId(request.getLoginId());

        if (bindingResult.hasErrors()) {
            log.error(bindingResult.toString());
            return "member/modify-info";
        }

        memberService.modifyMember(request, principal.getId());

        return "redirect:/my-page";
    }
}
