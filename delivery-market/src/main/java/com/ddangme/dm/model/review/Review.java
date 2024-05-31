package com.ddangme.dm.model.review;

import com.ddangme.dm.model.good.GoodOption;
import com.ddangme.dm.model.member.Member;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedBy
    @JoinColumn(name = "option_id")
    @ManyToOne
    private GoodOption option;

    private BigDecimal rating;

    private String content;

    private Integer point;

    private Boolean isBest;

    @CreatedBy
    @JoinColumn(name = "created_by")
    @ManyToOne
    private Member member;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @CreatedDate
    private LocalDateTime createdAt;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL)
    private List<ReviewPhoto> photos = new ArrayList<>();

    @OneToMany(mappedBy = "review")
    private List<ReviewLike> likes = new ArrayList<>();
}
