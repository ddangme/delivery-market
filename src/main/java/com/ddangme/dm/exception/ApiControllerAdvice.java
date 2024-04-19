package com.ddangme.dm.exception;

import com.ddangme.dm.dto.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

@Slf4j
@RestControllerAdvice
public class ApiControllerAdvice {

    @ExceptionHandler(DMException.class)
    public ResponseEntity<?> badRequestHandler(DMException exception) {
        log.error("[BAD_REQUEST_HANDLER] exception", exception);

        return ResponseEntity.status(exception.getErrorCode().getStatus())
                .body(
                        Response.error(exception.getErrorCode().name())
                );
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> exceptionHandler(RuntimeException exception) {
        log.error("Error occurs", exception);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Response.error(ErrorCode.INTERNAL_SERVER_ERROR.name()));
    }

    @ExceptionHandler({AddressException.class, MessagingException.class})
    public ResponseEntity<?> addressExceptionHandler(Exception exception) {
        log.error("address error occurs", exception);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Response.error(ErrorCode.INVALID_EMAIL_FORMAT.name()));
    }
}
