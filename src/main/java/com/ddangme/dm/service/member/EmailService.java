package com.ddangme.dm.service.member;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender emailSender;
    private final SpringTemplateEngine templateEngine;

    public String createCode() {
        return UUID.randomUUID().toString().substring(0, 10);
    }

    public MimeMessage createEmailForm(String email, String authCode) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = emailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, email);
        message.setSubject("[Delivery Market] 인증 번호");
        message.setFrom("tkdtjdtkddms@naver.com");
//        message.setText(setContext(authCode), "utf-8", "html");
        message.setText(authCode, "utf-8", "html");

        return message;
    }

    public String sendEmail(String toEmail) throws MessagingException, UnsupportedEncodingException {
        String authCode = createCode();
        MimeMessage emailForm = createEmailForm(toEmail, authCode);

        emailSender.send(emailForm);

        return authCode;
    }

    public String setContext(String code) {
        Context context = new Context();
        context.setVariable("code", code);
        return templateEngine.process("/member/mail", context);
    }
}
