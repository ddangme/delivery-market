package com.ddangme.dm.dto.member.request;

import com.ddangme.dm.dto.member.MemberDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModifyMemberRequest {

    private String loginId;
    private String name;
    private String password;
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}")
    private String newPassword;
    private String newPasswordCheck;
    private String phone;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthday;

    public static ModifyMemberRequest fromDTO(MemberDTO memberDTO) {
        return new ModifyMemberRequest(
                memberDTO.getLoginId(),
                memberDTO.getName(),
                null, null, null,
                memberDTO.getPhone(),
                memberDTO.getBirthday()
        );
    }

    public boolean notEqualsPasswordCheck() {
        return !newPassword.equals(newPasswordCheck);
    }

    public boolean notEqualsBeforePassword(String password) {
        return !this.password.equals(password);
    }

}
