package com.ddangme.dm.controller.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

    private String loginId;

    private String password;
    private String passwordCheck;

    @NotEmpty
    private String name;

    private String email;

    @NotEmpty
    private String phone;

    private String address;
    private String detail;
    private String zipCode;
    private LocalDate birthday;

}
