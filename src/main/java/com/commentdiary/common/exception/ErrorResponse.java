package com.commentdiary.common.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
@Builder
public class ErrorResponse {
    private int status;
    private String code;
    private String message;

    public static ResponseEntity<ErrorResponse> toCommonExceptionEntity(ErrorCode errorCode) {
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ErrorResponse.builder()
                        .status(errorCode.getHttpStatus().value())
                        .code(errorCode.name())
                        .message(errorCode.getMessage())
                        .build());
    }

    public static ResponseEntity<ErrorResponse> toAllExceptionEntity(String message) {
        ErrorCode errorCode = ErrorCode.SERVER_ERROR;
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ErrorResponse.builder()
                        .status(errorCode.getHttpStatus().value())
                        .code(errorCode.name())
                        .message(errorCode.getMessage())
                        .build());
    }
}
