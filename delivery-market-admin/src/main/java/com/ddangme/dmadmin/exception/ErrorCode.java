package com.ddangme.dmadmin.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error."),
    DUPLICATE_CATEGORY_NAME(HttpStatus.CONFLICT, "Duplicate category name"),
    NOT_EXIST_PARENT_CATEGORY(HttpStatus.NOT_FOUND, "Category does not exist"),

    ;


    private HttpStatus status;
    private String message;
}
