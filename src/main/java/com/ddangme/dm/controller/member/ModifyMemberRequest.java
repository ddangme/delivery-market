package com.ddangme.dm.controller.member;

import com.ddangme.dm.dto.member.MemberDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModifyMemberRequest {

    private String loginId;
    private String name;
    private String password;
    private String newPassword;
    private String newPasswordCheck;
    private String email;
    private String phone;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthday;

    public static ModifyMemberRequest fromDTO(MemberDTO memberDTO) {
        return new ModifyMemberRequest(
                memberDTO.getLoginId(),
                memberDTO.getName(),
                null, null, null,
                memberDTO.getEmail(),
                memberDTO.getPhone(),
                memberDTO.getBirthday()
        );
    }

}
