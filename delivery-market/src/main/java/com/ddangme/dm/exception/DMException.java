package com.ddangme.dm.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DMException extends RuntimeException {

    private ErrorCode errorCode;
    private String message;

    public DMException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
    }

}
