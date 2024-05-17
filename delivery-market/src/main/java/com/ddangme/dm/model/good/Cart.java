package com.ddangme.dm.model.good;

import com.ddangme.dm.model.member.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Entity
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @ToString.Exclude
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id")
    @ToString.Exclude
    private GoodOption option;

    private Integer count;

    private Boolean status;

    private Cart(Member member, GoodOption option, Integer count) {
        this.member = member;
        this.option = option;
        this.count = count;
        this.status = true;
    }

    public static Cart create(Member member, GoodOption option, Integer count) {
        return new Cart(member, option, count);
    }

    public void addCount(Integer count) {
        this.count += count;
    }
}
