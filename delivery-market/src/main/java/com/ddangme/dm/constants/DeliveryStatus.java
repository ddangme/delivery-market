package com.ddangme.dm.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DeliveryStatus {
    PENDING("배송 준비중"),
    SHIPPED("배송 중"),
    CANCELLED("구매 취소"),
    DELIVERED("배송 완료"),
    RETURN_REQUEST("반품 신청"),
    RETURNED("반품 완료"),
    RETURN_PROCESSED("반품 처리중"),
    ;

    private final String status;
}
