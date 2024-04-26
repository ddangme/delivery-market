package com.ddangme.dmadmin.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생하였습니다. 계속 발생할 경우 관리자에게 문의해 주세요."),
    DUPLICATE_CATEGORY_NAME(HttpStatus.CONFLICT, "이미 존재하는 이름입니다."),
    UNABLE_LENGTH_CATEGORY_NAME(HttpStatus.BAD_REQUEST, "카테고리 이름은 2자 ~ 15자로 입력해주세요."),
    NOT_EXIST_PARENT_CATEGORY(HttpStatus.NOT_FOUND, "상위 카테고리가 존재하지 않습니다."),

    ;


    private HttpStatus status;
    private String message;
}