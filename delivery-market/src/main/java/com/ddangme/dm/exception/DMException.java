package com.ddangme.dm.exception;

import lombok.Getter;

@Getter
public class DMException extends RuntimeException {

    private ErrorCode errorCode;
    private String message;

    public DMException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
    }

    public DMException(ErrorCode errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

}
