package com.ddangme.dmadmin.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DMAdminException extends RuntimeException {

    private ErrorCode errorCode;
    private String message;

    public DMAdminException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
    }

}
