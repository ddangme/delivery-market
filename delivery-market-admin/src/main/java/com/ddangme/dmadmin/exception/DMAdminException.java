package com.ddangme.dmadmin.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DMAdminException extends RuntimeException {

    private ErrorCode errorCode;
}
