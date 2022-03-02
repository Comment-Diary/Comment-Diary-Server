package com.commentdiary.src.member.controller;

import com.commentdiary.common.response.CommonResponse;
import com.commentdiary.src.member.dto.*;
import com.commentdiary.src.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.commentdiary.common.response.CommonResponseStatus.SUCCESS;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("")
    public CommonResponse<Void> signUp(@RequestBody @Valid SignUpRequest signUpRequest) {
        memberService.signUp(signUpRequest);
        return new CommonResponse<>(SUCCESS);
    }

    @PostMapping("/login")
    public CommonResponse<TokenResponse> login(@RequestBody LoginRequest loginRequest) {
        TokenResponse result = memberService.login(loginRequest);
        return new CommonResponse<>(result);
    }


    @PatchMapping("")
    public CommonResponse<Void> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        memberService.changePassword(changePasswordRequest);
        return new CommonResponse<>(SUCCESS);
    }

    @PostMapping("/logout")
    public CommonResponse<Void> logout(@RequestHeader(value = "REFRESH-TOKEN") String refreshToken) {
        memberService.logout();
        return new CommonResponse<>(SUCCESS);
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
