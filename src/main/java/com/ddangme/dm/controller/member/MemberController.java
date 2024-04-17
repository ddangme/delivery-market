package com.ddangme.dm.controller.member;

import com.ddangme.dm.dto.Response;
import com.ddangme.dm.exception.DMException;
import com.ddangme.dm.exception.ErrorCode;
import com.ddangme.dm.service.member.EmailService;
import com.ddangme.dm.service.member.MemberService;
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

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final EmailService emailService;


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

    @PostMapping("/api/id-duplicate-check")
    public ResponseEntity<?> idDuplicateCheck(@RequestBody String loginId) {
        if (memberService.searchMember(loginId).isPresent()) {
            throw new DMException(ErrorCode.DUPLICATED_LOGIN_ID);
        }
        return ResponseEntity.ok().body(Response.success());
    }

    @PostMapping("/api/email")
    public ResponseEntity<?> sendMail(@RequestBody String email, HttpSession session) throws MessagingException, UnsupportedEncodingException {
        String authCode = emailService.sendEmail(email);
        session.setAttribute("dm-auth-code", authCode);
        session.setMaxInactiveInterval(180);

        log.info("발급받은 authcode={}", authCode);
        return ResponseEntity.ok(Response.success());
    }

    @PostMapping("/api/email/auth-code")
    public ResponseEntity<?> checkAuthCode(@RequestBody String authCode, HttpSession session) {
        String saveAuthCode = (String) session.getAttribute("dm-auth-code");

        if (saveAuthCode == null) {
            throw new DMException(ErrorCode.AUTH_CODE_EXPIRED);
        }

        if (saveAuthCode.equals(authCode)) {
            session.removeAttribute("dm-auth-code");
            return ResponseEntity.ok(Response.success());
        }

        throw new DMException(ErrorCode.INVALID_VERIFICATION_CODE);
    }
}
