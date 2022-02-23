package com.commentdiary.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import static org.springframework.http.HttpStatus.*;

@Getter
public enum ErrorCode {


    /* 400 BAD_REQUEST : 잘못된 요청 */

    /* 401 UNAUTHORIZED : 인증되지 않은 사용자 */

    /* 403 Forbidden : 요청이 서버에 의해 거부되었음 */

    /* 404 NOT_FOUND : Resource를 찾을 수 없음 */
    MEMBER_NOT_FOUND(NOT_FOUND, "존재하지 않는 유저입니다."),
    NOT_MATCHED_CODE(NOT_FOUND, "인증 번호가 일치하지 않습니다."),
    INVALID_PASSWORD(NOT_FOUND, "비밀번호가 일치하지 않습니다"),


    /* 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
    DUPLICATED_EMAIL(CONFLICT, "이미 가입되어 있는 이메일입니다."),

    SERVER_ERROR(INTERNAL_SERVER_ERROR,"서버 내부에 에러가 발생했습니다." )


    ;

    private final HttpStatus httpStatus;
    private String message;


    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
