package com.ddangme.dm.model.member;


import com.ddangme.dm.model.Address;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Entity
@Builder
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE member SET deleted_at = NOW() WHERE member_id = ?")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String loginId;

    @Setter
    private String password;

    private String name;

    private String email;

    private String phone;

    @OneToMany(mappedBy = "member")
    private List<Address> address;

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
            , String phone, LocalDate birthday) {
        return new Member(loginId, password, name, email, phone, birthday);
    }

    private Member(String loginId, String password, String name) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.memberStatus = MemberStatus.NORMAL;
        this.memberRole = MemberRole.USER;
        this.benefitLevel = BenefitLevel.BASIC;
        this.point = 0L;
        this.cash = 0L;
    }

    private Member(String loginId, String password, String name, String email
            , String phone, LocalDate birthday) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.birthday = birthday;
        this.memberStatus = MemberStatus.NORMAL;
        this.memberRole = MemberRole.USER;
        this.benefitLevel = BenefitLevel.BASIC;
        this.point = 0L;
        this.cash = 0L;
    }

    public void modify(String newPassword, String phone, LocalDate birthday) {
        this.password = newPassword;
        this.phone = phone;
        this.birthday = birthday;
    }

}
