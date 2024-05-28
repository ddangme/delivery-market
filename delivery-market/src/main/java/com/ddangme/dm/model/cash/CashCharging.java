package com.ddangme.dm.model.cash;

import com.ddangme.dm.exception.DMException;
import com.ddangme.dm.exception.ErrorCode;
import com.ddangme.dm.constants.CashStatus;
import com.ddangme.dm.model.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
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

    @Enumerated(EnumType.STRING)
    private CashStatus status;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @CreatedDate
    private LocalDateTime requestAt;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDateTime responseAt;

    public static CashCharging create(Member member, Long amount) {
        return new CashCharging(member, amount, CashStatus.ASK);
    }

    private void validateAmount(Long amount) {
        if (amount == null || amount == 0) {
            throw new DMException(ErrorCode.IS_NULL_CASH_CHARGING_AMOUNT);
        }

        if (amount > 1_000_000) {
            throw new DMException(ErrorCode.MAX_OVER_CASH_CHARGING_AMOUNT);
        }
    }

    public CashCharging(Member member, Long amount, CashStatus status) {
        validateAmount(amount);
        this.member = member;
        this.amount = amount;
        this.status = status;
    }

    public void cancel() {
        if (status.equals(CashStatus.ASK)) {
            this.status = CashStatus.CANCEL;
        } else {
            throw new DMException(ErrorCode.IS_NON_CANCEL_CASH_CHARGING);
        }
    }
}
