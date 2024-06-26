package com.ddangme.dmadmin.model.cash;

import com.ddangme.dmadmin.exception.DMAdminException;
import com.ddangme.dmadmin.exception.ErrorCode;
import com.ddangme.dmadmin.model.admin.Admin;
import com.ddangme.dmadmin.model.constants.CashStatus;
import com.ddangme.dmadmin.model.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@ToString
@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public class CashCharging {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @ToString.Exclude
    Member member;

    private Long amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    @ToString.Exclude
    private Admin admin;

    @Enumerated(EnumType.STRING)
    private CashStatus status;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDateTime requestAt;


    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @LastModifiedDate
    private LocalDateTime responseAt;

    public void changeStatus(CashStatus cashStatus, Admin admin) {
        if (isNonModifiable()) {
            throw new DMAdminException(ErrorCode.IS_NON_MODIFIABLE_CASH_CHARGING_STATUS);
        }

        this.status = cashStatus;
        if (status.equals(CashStatus.YES)) {
            member.addCash(amount);
        }
        this.admin = admin;
    }

    private boolean isNonModifiable() {
        return status.equals(CashStatus.NO) || status.equals(CashStatus.CANCEL) || status.equals(CashStatus.YES);
    }

}