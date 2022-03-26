package com.commentdiary.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.commentdiary.common.response.CommonResponseStatus.SUCCESS;


@Getter
@AllArgsConstructor
@JsonPropertyOrder({"code", "message", "result"})
public class CommonResponse<T> {
    private final int code;
    private final String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;

    public CommonResponse(T result) {
        this.code = SUCCESS.getCode();
        this.message = SUCCESS.getMessage();
        this.result = result;
    }

    public CommonResponse(CommonResponseStatus status) {
        this.code = status.getCode();
        this.message = status.getMessage();
    }
}