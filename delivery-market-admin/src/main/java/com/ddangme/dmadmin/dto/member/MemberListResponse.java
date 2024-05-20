package com.ddangme.dmadmin.dto.member;

import com.ddangme.dmadmin.model.constants.BenefitLevel;
import com.ddangme.dmadmin.model.constants.MemberStatus;
import com.ddangme.dmadmin.model.member.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberListResponse {

    private Long id;
    private String name;
    private String memberStatus;
    private String benefitLevel;
    private String phone;
    private Long cash;
    private Long point;

    public static MemberListResponse fromEntity(Member entity) {
        return new MemberListResponse(
                entity.getId(),
                entity.getName(),
                entity.getMemberStatus().getStatus(),
                entity.getBenefitLevel().getLevel(),
                entity.getPhone(),
                entity.getCash(),
                entity.getPoint()
        );
    }
}
