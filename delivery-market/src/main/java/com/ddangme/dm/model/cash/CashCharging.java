package com.ddangme.dm.model.cash;

import com.ddangme.dm.model.constants.CashStatus;
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

    public static CashCharging create(Member member, Long amount) {
        return new CashCharging(member, amount, CashStatus.ASK);
    }

    public CashCharging(Member member, Long amount, CashStatus status) {
        this.member = member;
        this.amount = amount;
        this.status = status;
    }
}
