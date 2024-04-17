package com.ddangme.dm.config;

import com.ddangme.dm.dto.member.KakaoOAuth2Response;
import com.ddangme.dm.dto.member.MemberPrincipal;
import com.ddangme.dm.exception.DMException;
import com.ddangme.dm.exception.ErrorCode;
import com.ddangme.dm.service.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;

import java.util.UUID;

@Configuration
@Slf4j
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .mvcMatchers("/css/**", "/js/**", "/img/**", "/error/**", "/", "/sign-up", "/api/id-duplicate-check", "/api/email/**")
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                )
                .formLogin(Customizer.withDefaults())
                .logout(logout -> logout.logoutSuccessUrl("/"))
                .oauth2Login(oAuth -> oAuth
                        .userInfoEndpoint(userInfo -> userInfo.userService(oAuth2UserService)))
                .csrf().disable()
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService(MemberService memberService) {
        return loginId -> memberService
                .searchMember(loginId)
                .map(MemberPrincipal::fromDTO)
                .orElseThrow(() -> new DMException(ErrorCode.NOT_FOUND_MEMBER));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService(
            MemberService memberService,
            PasswordEncoder passwordEncoder
    ) {
        final DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();

        return userRequest -> {
            OAuth2User oAuth2User = delegate.loadUser(userRequest);

            KakaoOAuth2Response kakaoResponse = KakaoOAuth2Response.from(oAuth2User.getAttributes());
            String registrationId = userRequest.getClientRegistration().getRegistrationId();
            String providerId = String.valueOf(kakaoResponse.id());
            String loginId = registrationId + "_" + providerId;
            String dummyPassword = passwordEncoder.encode("{bcrypt}" + UUID.randomUUID());

            return memberService.searchMember(loginId)
                    .map(MemberPrincipal::fromDTO)
                    .orElseGet(() ->
                            MemberPrincipal.fromDTO(
                                    memberService.saveMember(loginId, dummyPassword, kakaoResponse.nickname())
                            ));
        };
    }
}
