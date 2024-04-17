package com.ddangme.dm.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error."),
    NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND, "Login Id or Password not founded."),
    DUPLICATED_LOGIN_ID(HttpStatus.CONFLICT, "Login ID is duplicated."),
    INVALID_VERIFICATION_CODE(HttpStatus.UNAUTHORIZED, "The auth code is not valid."),
    AUTH_CODE_EXPIRED(HttpStatus.FORBIDDEN, "Authentication timed out."),
    INVALID_EMAIL_FORMAT(HttpStatus.BAD_REQUEST, "Invalid email.")
    ;


    private HttpStatus status;
    private String message;
}
