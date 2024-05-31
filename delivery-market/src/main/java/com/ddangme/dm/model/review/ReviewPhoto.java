package com.ddangme.dm.model.review;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class ReviewPhoto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;

    private String photo;

}
