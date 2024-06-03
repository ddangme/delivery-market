package com.ddangme.dm.dto.review;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ReviewRequest {

    private Long optionId;
    private Long orderGoodId;
    private BigDecimal rating;
    private String content;
    private Boolean secret;

}
