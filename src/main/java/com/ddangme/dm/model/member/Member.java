package com.ddangme.dm.model.member;


import com.ddangme.dm.model.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Entity
@Builder
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE member SET deleted_at = NOW() WHERE id = ?")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String loginId;

    private String password;

    private String name;

    private String email;

    private String phone;

    @Embedded
    private Address address;

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
    @CreatedDate
    private LocalDateTime createdAt;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDateTime deletedAt;

    public static Member signUp(String loginId, String password, String name) {
        return new Member(loginId, password, name);
    }

    public static Member signUp(String loginId, String password, String name, String email
            , String phone, String address, String detail, Integer zipCode, LocalDate birthday) {
        return new Member(loginId, password, name, email, phone, new Address(address, detail, zipCode), birthday);
    }

    private Member(String loginId, String password, String name) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.memberStatus = MemberStatus.NORMAL;
        this.memberRole = MemberRole.USER;
        this.benefitLevel = BenefitLevel.BASIC;
    }

    private Member(String loginId, String password, String name, String email
            , String phone, Address address, LocalDate birthday) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.birthday = birthday;
        this.memberStatus = MemberStatus.NORMAL;
        this.memberRole = MemberRole.USER;
        this.benefitLevel = BenefitLevel.BASIC;
    }

}
