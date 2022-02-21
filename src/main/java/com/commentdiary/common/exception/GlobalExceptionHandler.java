package com.commentdiary.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CommonException.class)
    public ResponseEntity<ErrorResponse> commonException (CommonException e) {
        log.error("Handle CommonException: {}", e.getMessage());
        return ErrorResponse.toCommonExceptionEntity(e.getErrorCode());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> allHandleException(Exception e){
        log.error("Handle All Exception: {}", e.getMessage());
        return ErrorResponse.toAllExceptionEntity(e.getMessage());
    }
}
