package com.commentdiary.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CommonException.class)
    public ResponseEntity<ErrorResponse> commonException (CommonException e) {
        log.error("Handle CommonException: {}", e.getMessage());
        return ErrorResponse.toCommonExceptionEntity(e.getErrorCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> validationException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();

        Map<String, String> errors = new HashMap<>();
        bindingResult.getAllErrors()
                .forEach(c -> errors.put(((FieldError) c).getField(), c.getDefaultMessage()));

        return ErrorResponse.toValidationException(errors);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> allHandleException(Exception e){
        log.error("Handle All Exception: {}", e.getMessage());
        return ErrorResponse.toAllExceptionEntity(e.getMessage());
    }

}
