package com.ddangme.dm.model.review;

import com.ddangme.dm.model.good.GoodOption;
import com.ddangme.dm.model.member.Member;
import com.ddangme.dm.model.order.OrderGood;
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

    @JoinColumn(name = "option_id")
    @ManyToOne
    private GoodOption option;

    @ManyToOne
    @JoinColumn(name = "order_good_id")
    private OrderGood orderGood;

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

    public Review(GoodOption option, OrderGood orderGood, BigDecimal rating, String content, List<String> filenames) {
        this.option = option;
        this.orderGood = orderGood;
        this.rating = rating;
        this.content = content;
        addPhotos(filenames);
        calculatePoint();
    }

    private void calculatePoint() {
        if (photos.isEmpty()) {
            this.point = (int) (getPayPrice() * 0.01);
        } else {
            this.point = (int) (getPayPrice() * 0.02);
        }
    }

    private Long getPayPrice() {
        if (orderGood.getDiscountPrice() == null) {
            return orderGood.getPrice();
        }
        return orderGood.getDiscountPrice();
    }

    private void addPhotos(List<String> filenames) {
        filenames.forEach(filename -> {
            ReviewPhoto reviewPhoto = new ReviewPhoto(this, filename);
            photos.add(reviewPhoto);
        });
    }
}
