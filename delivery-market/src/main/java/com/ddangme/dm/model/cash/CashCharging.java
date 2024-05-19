package com.ddangme.dm.model.cash;

import com.ddangme.dm.model.constants.CashStatus;
import com.ddangme.dm.model.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
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

    private CashStatus status;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDateTime requestAt;

}
