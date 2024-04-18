package com.ddangme.dm.model;

import com.ddangme.dm.model.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String road;
    private String detail;
    private Integer zipcode;

    private Boolean main;

    private String recipientName;
    private String recipientPhone;

    public Address(Member member, String road, String detail, Integer zipcode, boolean main) {
        this.member = member;
        this.road = road;
        this.detail = detail;
        this.zipcode = zipcode;
        this.main = main;
        this.recipientName = member.getName();
        this.recipientPhone = member.getPhone();
    }
}
