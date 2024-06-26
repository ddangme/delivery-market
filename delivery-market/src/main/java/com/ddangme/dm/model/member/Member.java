package com.ddangme.dm.model.member;


import com.ddangme.dm.exception.DMException;
import com.ddangme.dm.exception.ErrorCode;
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
@SQLDelete(sql = "UPDATE member SET deleted_at = NOW() WHERE id = ?")
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

    @OneToMany(mappedBy = "member")
    private List<Address> addresses;

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

    public Address getMainAddress() {
        for (Address value : addresses) {
            if (value.isMain()) {
                return value;
            }
        }

        throw new DMException(ErrorCode.NOT_EXIST_MAIN_ADDRESS);
    }

    public Member(Long id, String loginId, String name, BenefitLevel benefitLevel, MemberStatus memberStatus) {
        this.id = id;
        this.loginId = loginId;
        this.name = name;
        this.benefitLevel = benefitLevel;
        this.memberStatus = memberStatus;
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

    public void canBuy(Long payMoney) {
        if (point + cash < payMoney) {
            throw new DMException(ErrorCode.NOT_ENOUGH_CASH);
        }
    }

    public void usePointAndCash(Long payMoney) {
        canBuy(payMoney);

        if (payMoney - point > 0) {
            cash = cash - payMoney - point;
            point = 0L;
        } else {
            point = payMoney - point;
        }
    }

    public Long usePoint(Long payMoney) {
        if (payMoney - point > 0) {
            payMoney = payMoney - point;
            point = 0L;
        } else {
            point = payMoney - point;
            payMoney = 0L;
        }

        return payMoney;
    }

    public void useCash(Long remainMoney) {
        cash = cash - remainMoney;
    }

    public void addPoint(Integer point) {
        this.point += point;
    }

}
