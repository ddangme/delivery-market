package com.ddangme.dm.model.good;

import com.ddangme.dm.model.member.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.parameters.P;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Pick {

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

    private Pick(Member member, Good good) {
        this.member = member;
        this.good = good;
    }

    public static Pick create(Member member, Good good) {
        return new Pick(member, good);
    }
}
