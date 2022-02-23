package com.commentdiary.common.response;

import lombok.Getter;

@Getter
public enum CommonResponseStatus {

    SUCCESS(1000, "요청에 성공하였습니다.")
    ;

    private final int code;
    private final String message;

    CommonResponseStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
