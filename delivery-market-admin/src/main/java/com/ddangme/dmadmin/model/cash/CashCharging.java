package com.ddangme.dmadmin.model.cash;

import com.ddangme.dmadmin.model.admin.Admin;
import com.ddangme.dmadmin.model.constants.CashStatus;
import com.ddangme.dmadmin.model.member.Member;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    @ToString.Exclude
    private Admin admin;
    private CashStatus status;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDateTime requestAt;

}