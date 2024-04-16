package com.ddangme.dm.config;

import com.ddangme.dm.dto.MemberPrincipal;
import com.ddangme.dm.exception.DMException;
import com.ddangme.dm.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .mvcMatchers("/css/**", "/js/**", "/img/**", "/error/**", "/")
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                )
                .formLogin(Customizer.withDefaults())
                .logout(logout -> logout.logoutSuccessUrl("/"))
                .csrf().disable()
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService(MemberService memberService) {
        return loginId -> memberService
                .searchMember(loginId)
                .map(MemberPrincipal::fromDTO)
                .orElseThrow(DMException::new);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
