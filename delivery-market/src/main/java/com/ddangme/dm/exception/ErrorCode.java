package com.ddangme.dm.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error."),
    NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND, "Login Id or Password not founded."),
    NOT_FOUND_ACCOUNT(HttpStatus.NOT_FOUND, "This Account does not exist"),
    DUPLICATED_LOGIN_ID(HttpStatus.CONFLICT, "Login ID is duplicated."),
    INVALID_VERIFICATION_CODE(HttpStatus.UNAUTHORIZED, "The auth code is not valid."),
    AUTH_CODE_EXPIRED(HttpStatus.FORBIDDEN, "Authentication timed out."),
    INVALID_EMAIL_FORMAT(HttpStatus.BAD_REQUEST, "Invalid email."),
    NOT_FOUND_ADDRESS(HttpStatus.NOT_FOUND, "This Address does not exist."),
    IS_NOT_ADDRESS_OWNER(HttpStatus.UNAUTHORIZED, "Is not address owner."),
    CANNOT_DELETE_DEFAULT_ADDRESS(HttpStatus.FORBIDDEN, "The default address cannot be deleted."),


    GOODS_LOADING_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "상품 리스트 조회에 실패하였습니다."),
    ;


    private HttpStatus status;
    private String message;
}
