package com.ddangme.dm.model.review;

import com.ddangme.dm.model.member.Member;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;

import javax.persistence.*;

@Getter
@Entity
public class ReviewLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;

    @CreatedBy
    @JoinColumn(name = "member_id")
    @ManyToOne
    private Member member;
}
