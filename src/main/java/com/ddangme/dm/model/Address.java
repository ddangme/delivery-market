package com.ddangme.dm.model;

import com.ddangme.dm.model.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @NotNull
    private String road;

    private String detail;

    @Column(length = 6)
    @NotNull
    private Integer zipcode;

    private Boolean main;

    @NotNull
    @Column(length = 20)
    private String recipientName;

    @NotNull
    @Column(length = 20)
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

    public Address(Member member, String road, String detail, Integer zipcode, Boolean main, String recipientName, String recipientPhone) {
        this.member = member;
        this.road = road;
        this.detail = detail;
        this.zipcode = zipcode;
        this.main = Objects.requireNonNullElse(main, false);
        this.recipientName = recipientName;
        this.recipientPhone = recipientPhone;
    }

    public void setMainToFalse() {
        main = false;
    }

    public void setMainToTrue() {
        main = true;
    }
}
