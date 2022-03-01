package com.commentdiary.jwt;

import com.commentdiary.common.exception.ErrorCode;
import com.commentdiary.common.exception.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        // 유효한 자격증명을 제공하지 않고 접근하려 할때 401
        String exception = (String) request.getAttribute("exception");
        ErrorCode errorCode;

        if (exception.equals("ExpiredJwtException")) {
            errorCode = ErrorCode.EXPIRED_TOKEN;
            sendError(response, errorCode);
            return;
        }
        if (exception.equals("MalformedJwtException")) {
            errorCode = ErrorCode.WRONG_TOKEN_SIGNATURE;
            sendError(response, errorCode);
            return;
        }

        if (exception.equals("UnsupportedJwtException")) {
            errorCode = ErrorCode.UNSUPPORTED_TOKEN;
            sendError(response, errorCode);
            return;
        }

        if (exception.equals("IllegalArgumentException")) {
            errorCode = ErrorCode.WRONG_TOKEN;
            sendError(response, errorCode);
            return;
        }
    }
    private void sendError(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        response.getWriter().write(objectMapper.writeValueAsString(ErrorResponse.builder()
                .status(errorCode.getHttpStatus().value())
                .code(errorCode.name())
                .message(errorCode.getMessage())
                .build()));
    }
}