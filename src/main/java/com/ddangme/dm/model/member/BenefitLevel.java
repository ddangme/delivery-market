package com.ddangme.dm.model.member;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BenefitLevel {
    BASIC("일반"),
    VIP("VIP");

    private final String level;
}
