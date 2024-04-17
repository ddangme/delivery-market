package com.ddangme.dm.controller.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

    @Pattern(regexp = "^[a-zA-Z0-9]{5,20}$")
    private String loginId;

    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}")
    private String password;
    private String passwordCheck;

    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-zA-Z0-9]{2,10}$")
    private String name;

    @Email
    @NotEmpty
    private String email;

    @Pattern(regexp = "^\\d{10,11}$")
    private String phone;

    @NotEmpty
    private String road;

    private String detail;

    @NotNull
    private Integer zipcode;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthday;

}
