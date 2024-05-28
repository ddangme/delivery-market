package com.ddangme.dm.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CashStatus {
    CANCEL("취소"),
    ASK("요청"),
    YES("승낙"),
    NO("거절"),
    HOLD("보류"),
    ;

    private String status;

}

