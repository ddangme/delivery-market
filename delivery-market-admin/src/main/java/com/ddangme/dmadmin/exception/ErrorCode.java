package com.ddangme.dmadmin.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생하였습니다. 계속 발생할 경우 관리자에게 문의해 주세요."),
    DUPLICATE_CATEGORY_NAME(HttpStatus.CONFLICT, "중복된 카테고리 이름이 존재합니다."),
    DUPLICATE_PARENT_CATEGORY_NAME(HttpStatus.CONFLICT, "상위 카테고리와 하위 카테고리 이름은 같을 수 없습니다."),
    UNABLE_LENGTH_CATEGORY_NAME(HttpStatus.BAD_REQUEST, "카테고리 이름은 2자 ~ 15자로 입력해주세요."),
    NOT_EXIST_PARENT_CATEGORY(HttpStatus.NOT_FOUND, "상위 카테고리가 존재하지 않습니다."),
    EXIST_CHILD_CATEGORY(HttpStatus.BAD_REQUEST, "하위 카테고리가 존재합니다."),
    NOT_EXIST_CATEGORY(HttpStatus.NOT_FOUND, "존재하지 않는 카테고리입니다."),
    NOT_CHOICE_CATEGORY(HttpStatus.BAD_REQUEST, "선택된 카테고리가 없습니다."),
    NOT_EXIST_ADMIN_ACCOUNT(HttpStatus.UNAUTHORIZED, "존재하지 않는 관리자입니다."),
    ADMIN_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 계정입니다."),


    FIELD_IS_NULL(HttpStatus.BAD_REQUEST, "필수 값을 입력해 주세요."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),


    NOT_EXIST_GOODS(HttpStatus.NOT_FOUND, "존재하지 않는 상품입니다."),



    ;


    private HttpStatus status;
    private String message;
}
