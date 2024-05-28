package com.ddangme.dm.model.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PackagingType {
    REFRIGERATED("냉장"),
    FROZEN("냉동"),
    ROOM_TEMPERATURE("실온"),
    ;
    private String type;
}
