package com.ddangme.dmadmin.model.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberStatus {
    NORMAL("정상"),
    WITHDRAWN("탈퇴"),
    SUSPENDED("정지"),
    ;

    private final String status;
}
