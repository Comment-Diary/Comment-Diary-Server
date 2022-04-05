package com.commentdiary.src.member.controller;

import com.commentdiary.common.response.CommonResponse;
import com.commentdiary.src.member.dto.*;
import com.commentdiary.src.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.HashMap;
import java.util.Map;

import static com.commentdiary.common.exception.ErrorCode.INVALID_EMAIL_ADDRESS;
import static com.commentdiary.common.response.CommonResponseStatus.SUCCESS;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/sign-up")
    public CommonResponse<Void> signUp(@Valid @RequestBody SignUpRequest signUpRequest, BindingResult result) {
        if(result.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            for(FieldError error : result.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
            }
            return new CommonResponse(INVALID_EMAIL_ADDRESS);
        }
        memberService.signUp(signUpRequest);
        return new CommonResponse<>(SUCCESS);
    }

    @PostMapping("/login")
    public CommonResponse<TokenResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        TokenResponse result = memberService.login(loginRequest);
        return new CommonResponse<>(result);
    }

    @GetMapping("")
    public CommonResponse<MyPageResponse> myPage() {
        MyPageResponse result = memberService.myPage();
        return new CommonResponse<>(result);
    }

    @PatchMapping("")
    public CommonResponse<Void> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        memberService.changePassword(changePasswordRequest);
        return new CommonResponse<>(SUCCESS);
    }

    @DeleteMapping("/logout")
    public CommonResponse<Void> logout(@RequestHeader(value = "X-AUTH-TOKEN") String accessToken) {
        memberService.logout(accessToken);
        return new CommonResponse<>(SUCCESS);
    }

    @PatchMapping("/push")
    public CommonResponse<PushDto> push() {
        PushDto result = memberService.push();
        return new CommonResponse<>(result);
    }

    @DeleteMapping("")
    public CommonResponse<Void> delete() {
        memberService.delete();
        return new CommonResponse<>(SUCCESS);
    }

    @PostMapping("/reissue")
    public CommonResponse<TokenResponse> reissue(@RequestHeader(value = "X-AUTH-TOKEN") String accessToken, @RequestHeader(value = "REFRESH-TOKEN") String refreshToken) {
        TokenResponse reuslt = memberService.reissue(accessToken, refreshToken);
        return new CommonResponse<>(reuslt);
    }
}
