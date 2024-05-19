package com.ddangme.dmadmin.model.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CashStatus {
    YES("승낙"),
    NO("거절"),
    HOLD("보류"),
    ;

    private String status;

}