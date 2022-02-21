package com.commentdiary.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import static org.springframework.http.HttpStatus.*;

@Getter
public enum ErrorCode {

    INVALID_PASSWORD(CONFLICT, "비밀번호가 일치하지 않습니다"),


    SERVER_ERROR(INTERNAL_SERVER_ERROR,"서버 내부에 에러가 발생했습니다." )
    ;

    private final HttpStatus httpStatus;
    private String message;


    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
