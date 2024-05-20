package com.ddangme.dmadmin.dto.cash;

import com.ddangme.dmadmin.model.constants.CashStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CashListProjection {

    private Long id;
    private Long amount;
    private String status;
    private Long memberId;
    private String memberName;
    private Long adminId;
    private String adminName;
    private LocalDateTime requestAt;
    private LocalDateTime responseAt;

    @QueryProjection
    public CashListProjection(Long id, Long amount, CashStatus status, Long memberId, String memberName, Long adminId, String adminName, LocalDateTime requestAt, LocalDateTime responseAt) {
        this.id = id;
        this.amount = amount;
        this.status = status.getStatus();
        this.memberId = memberId;
        this.memberName = memberName;
        this.adminId = adminId;
        this.adminName = adminName;
        this.requestAt = requestAt;
        this.responseAt = responseAt;
    }
}
