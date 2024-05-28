package com.ddangme.dm.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public enum OrderHistory {
    THREE_MONTHS(3),
    SIX_MONTHS(6),
    ONE_YEAR(12),
    THREE_YEARS(36);

    private final int months;

    public LocalDateTime getStartDateTime() {
        return LocalDateTime.now().minusMonths(this.months);
    }
}
