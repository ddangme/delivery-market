package com.ddangme.dm.dto.member;

import com.ddangme.dm.model.Address;
import com.ddangme.dm.model.member.BenefitLevel;
import com.ddangme.dm.model.member.Member;
import com.ddangme.dm.model.member.MemberStatus;
import com.ddangme.dm.model.member.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class MemberDTO {

    private Long id;

    private String loginId;

    private String password;

    private String name;

    private String email;

    private String phone;

    private Address address;

    private BenefitLevel benefitLevel;

    private MemberRole memberRole;

    private LocalDate birthday;

    private Long point;

    private Long cash;

    private MemberStatus memberStatus;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

    public static MemberDTO fromEntity(Member entity) {
        return new MemberDTO(
            entity.getId(),
            entity.getLoginId(),
            entity.getPassword(),
            entity.getName(),
            entity.getEmail(),
            entity.getPhone(),
            entity.getAddress(),
            entity.getBenefitLevel(),
            entity.getMemberRole(),
            entity.getBirthday(),
            entity.getPoint(),
            entity.getCash(),
            entity.getMemberStatus(),
            entity.getCreatedAt(),
            entity.getUpdatedAt(),
            entity.getDeletedAt()
        );
    }

}
