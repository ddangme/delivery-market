package com.ddangme.dm.model.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AdminRole {
    SUPER_ADMIN("최고 관리자"),
    ADMIN("일반 관리자"),
    ;

    private String role;


}
