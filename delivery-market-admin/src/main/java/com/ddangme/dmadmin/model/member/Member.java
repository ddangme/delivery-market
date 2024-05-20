package com.ddangme.dmadmin.model.member;

import com.ddangme.dmadmin.model.constants.BenefitLevel;
import com.ddangme.dmadmin.model.constants.MemberRole;
import com.ddangme.dmadmin.model.constants.MemberStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String loginId;

    @Setter
    private String password;

    private String name;

    private String email;

    private String phone;

    @Enumerated(EnumType.STRING)
    private BenefitLevel benefitLevel;

    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;

    private LocalDate birthday;

    private Long point;

    private Long cash;

    @Enumerated(EnumType.STRING)
    private MemberStatus memberStatus;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDateTime createdAt;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDateTime updatedAt;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDateTime deletedAt;

    public void addCash(Long plusCash) {
        this.cash += plusCash;
    }
}
