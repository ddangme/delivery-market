package com.ddangme.dm.model.member;

import lombok.Getter;

@Getter
public enum MemberStatus {
    NORMAL,
    WITHDRAWN,// 탈퇴
    SUSPENDED // 정지
}
