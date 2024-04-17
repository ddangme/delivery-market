package com.ddangme.dm.controller.member;

import com.ddangme.dm.dto.Response;
import com.ddangme.dm.exception.DMException;
import com.ddangme.dm.exception.ErrorCode;
import com.ddangme.dm.service.member.EmailService;
import com.ddangme.dm.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;
    private final EmailService emailService;


    @PostMapping("/api/id-duplicate-check")
    public ResponseEntity<?> idDuplicateCheck(@RequestBody String loginId) {
        if (memberService.searchMember(loginId).isPresent()) {
            throw new DMException(ErrorCode.DUPLICATED_LOGIN_ID);
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/email")
    public ResponseEntity<?> sendMail(@RequestBody String email, HttpSession session) throws MessagingException, UnsupportedEncodingException {
        String authCode = emailService.sendEmail(email);
        session.setAttribute("dm-auth-code", authCode);
        session.setMaxInactiveInterval(180);

        log.info("발급받은 authcode={}", authCode);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/email/auth-code")
    public ResponseEntity<?> checkAuthCode(@RequestBody String authCode, HttpSession session) {
        String saveAuthCode = (String) session.getAttribute("dm-auth-code");

        if (saveAuthCode == null) {
            throw new DMException(ErrorCode.AUTH_CODE_EXPIRED);
        }

        if (saveAuthCode.equals(authCode)) {
            session.removeAttribute("dm-auth-code");
            return ResponseEntity.ok().build();
        }

        throw new DMException(ErrorCode.INVALID_VERIFICATION_CODE);
    }

    @PostMapping("/api/member/find/id")
    public ResponseEntity<String> findId(@RequestBody MemberFindRequest memberFindRequest) {
        log.info("request={}", memberFindRequest);
        String loginId = memberService.findLoginId(memberFindRequest);

        return ResponseEntity.ok(loginId);
    }

    @PostMapping("/api/member/find/password")
    public ResponseEntity<Void> findPassword(@RequestBody MemberFindRequest memberFindRequest) throws MessagingException, UnsupportedEncodingException {
        log.info("request={}", memberFindRequest);
        memberService.findLoginId(memberFindRequest);

        String newPassword = emailService.sendEmail(memberFindRequest.getEmail());
        log.info("newPassword={}", newPassword);
        memberService.setPassword(memberFindRequest, newPassword);

        return ResponseEntity.ok().build();
    }
}
