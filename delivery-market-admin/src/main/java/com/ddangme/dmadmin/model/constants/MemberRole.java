package com.ddangme.dmadmin.model.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberRole {
    USER("사용자"),
    ADMIN("관리자");

    private final String type;
}
