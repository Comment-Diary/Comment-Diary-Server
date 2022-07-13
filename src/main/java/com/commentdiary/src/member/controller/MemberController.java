package com.commentdiary.src.member.controller;

import com.commentdiary.common.response.CommonResponse;
import com.commentdiary.src.member.dto.*;
import com.commentdiary.src.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.HashMap;
import java.util.Map;

import static com.commentdiary.common.exception.ErrorCode.INVALID_EMAIL_ADDRESS;
import static com.commentdiary.common.response.CommonResponseStatus.SUCCESS;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/sign-up")
    public CommonResponse<Void> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        log.info("[MemberController] signUp");
        memberService.signUp(signUpRequest);
        return new CommonResponse<>(SUCCESS);
    }

    @PostMapping("/login")
    public CommonResponse<TokenResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        TokenResponse result = memberService.login(loginRequest);
        log.info("[MemberController] login");
        return new CommonResponse<>(result);
    }

    @PostMapping("/auth-login")
    public CommonResponse<AuthLoginResponse> authLogin(@RequestBody AuthLoginRequest authLoginRequest) {
        AuthLoginResponse result = memberService.authLogin(authLoginRequest);
        log.info("[MemberController] authLogin");
        return new CommonResponse<>(result);
    }

    @PostMapping("/oauth-sign-up")
    public CommonResponse<Void> oauthSignUp(@RequestBody OAuthSignUpRequest oAuthSignUpRequest, Authentication authentication) {
        memberService.oauthSignUp(oAuthSignUpRequest);
        log.info("[MemberController] oauthSignUp, id : {}", authentication.getName());
        return new CommonResponse<>(SUCCESS);
    }

    @GetMapping("")
    public CommonResponse<MyPageResponse> myPage(Authentication authentication) {
        MyPageResponse result = memberService.myPage();
        log.info("[MemberController] myPage, id : {}", authentication.getName());
        return new CommonResponse<>(result);
    }

    @PatchMapping("")
    public CommonResponse<Void> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest, Authentication authentication) {
        memberService.changePassword(changePasswordRequest);
        log.info("[MemberController] changePassword, id : {}", authentication.getName());
        return new CommonResponse<>(SUCCESS);
    }

    @DeleteMapping("/logout")
    public CommonResponse<Void> logout(@RequestHeader(value = "X-AUTH-TOKEN") String accessToken) {
        memberService.logout(accessToken);
        log.info("[MemberController] logout");
        return new CommonResponse<>(SUCCESS);
    }

    @PatchMapping("/push")
    public CommonResponse<PushDto> push(Authentication authentication) {
        PushDto result = memberService.push();
        log.info("[MemberController] push, id : {}", authentication.getName());
        return new CommonResponse<>(result);
    }

    @DeleteMapping("")
    public CommonResponse<Void> delete(Authentication authentication) {
        memberService.delete();
        log.info("[MemberController] delete, id : {}", authentication.getName());
        return new CommonResponse<>(SUCCESS);
    }

    @PostMapping("/reissue")
    public CommonResponse<TokenResponse> reissue(@RequestHeader(value = "X-AUTH-TOKEN") String accessToken, @RequestHeader(value = "REFRESH-TOKEN") String refreshToken) {
        TokenResponse reuslt = memberService.reissue(accessToken, refreshToken);
        log.info("[MemberController] reissue");
        return new CommonResponse<>(reuslt);
    }
}
