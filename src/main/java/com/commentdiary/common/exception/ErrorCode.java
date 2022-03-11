package com.commentdiary.common.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import static org.springframework.http.HttpStatus.*;

@Getter
public enum ErrorCode {

    /* 400 BAD_REQUEST : 잘못된 요청 */

    /* 401 UNAUTHORIZED : 인증되지 않은 사용자 */
    WRONG_TOKEN_SIGNATURE(UNAUTHORIZED, "잘못된 JWT 서명입니다."),
    EXPIRED_TOKEN(UNAUTHORIZED, "만료된 JWT 토큰입니다."),
    UNSUPPORTED_TOKEN(UNAUTHORIZED, "지원되지 않는 JWT 토큰입니다."),
    WRONG_TOKEN(UNAUTHORIZED, "JWT 토큰이 잘못되었습니다."),
    EXPIRED_REFRESH_TOKEN(UNAUTHORIZED, "만료된 Refresh JWT 토큰입니다."),
    INVALID_REFRESH_TOKEN(UNAUTHORIZED, "Refresh 토큰이 유효하지 않습니다."),


    /* 403 Forbidden : 요청이 서버에 의해 거부되었음 */
    FAILED_TO_SEND_EMAIL(FORBIDDEN, "이메일 전송에 실패하였습니다."),

    /* 404 NOT_FOUND : Resource를 찾을 수 없음 */
    NOT_FOUND_MEMBER(NOT_FOUND, "존재하지 않는 유저입니다."),
    NOT_FOUND_EMAIL(NOT_FOUND, "이메일을 찾을 수 없습니다."),
    NOT_MATCHED_CODE(NOT_FOUND, "인증 번호가 일치하지 않습니다."),
    INVALID_PASSWORD(NOT_FOUND, "비밀번호가 일치하지 않습니다"),
    NOT_MATCHED_DIARY(NOT_FOUND, "일치하는 일기가 없습니다."),
    NOT_FOUND_DIARY(NOT_FOUND, "일기를 찾을 수 없습니다."),
    ALREADY_LOGOUT(NOT_FOUND, "로그아웃된 유저입니다."),
    NOT_MATCHED_TOKEN(NOT_FOUND, "JWT 토큰 정보가 일치하지 않습니다"),
    NOT_MATCHED_COMMENT(NOT_FOUND, "일치하는 코멘트가 없습니다."),
    NOT_FOUND_DELIVERY(NOT_FOUND, "전달된 일기가 존재하지 않습니다."),

    /* 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
    DUPLICATED_EMAIL(CONFLICT, "이미 가입되어 있는 이메일입니다."),
    ALREADY_LIKE(CONFLICT, "이미 좋아요를 눌렀습니다."),


    SERVER_ERROR(INTERNAL_SERVER_ERROR,"서버 내부에 에러가 발생했습니다." )


    ;

    private final HttpStatus httpStatus;
    private String message;


    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
