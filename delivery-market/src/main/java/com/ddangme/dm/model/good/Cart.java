package com.ddangme.dm.model.good;

import com.ddangme.dm.model.member.Member;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Entity
@ToString
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @ToString.Exclude
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "good_id")
    @ToString.Exclude
    private Good good;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id")
    @ToString.Exclude
    private GoodOption goodOption;

    private Long count;

    private Boolean status;
}
