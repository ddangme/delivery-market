package com.ddangme.dmadmin.config;

import com.ddangme.dmadmin.dto.admin.AdminPrincipal;
import com.ddangme.dmadmin.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Slf4j
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .mvcMatchers("/css/**", "/js/**", "/img/**", "/error/**")
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
    public UserDetailsService userDetailsService(AdminService adminService) {
        return email -> adminService
                .findByEmail(email)
                .map(AdminPrincipal::fromDTO)
                .orElseThrow(() -> new UsernameNotFoundException("유저를 찾을 수 없습니다 - email: " + email));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
