package com.ddangme.dmadmin.model.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SaleStatus {
    AVAILABLE("판매중"),
    SOLD_OUT("품절"),
    DISCONTINUED("생산 중단으로 판매 종료"),
    ON_SALE("할인 중"),
    RESTOCKING("재고 준비 중"),
    END("판매 종료"),
    ;

    private String status;

}
