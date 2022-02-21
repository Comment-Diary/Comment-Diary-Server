package com.commentdiary.src.member.controller;

import com.commentdiary.common.response.CommonResponse;
import com.commentdiary.src.member.dto.JwtResponse;
import com.commentdiary.src.member.dto.LoginRequest;
import com.commentdiary.src.member.dto.SignUpRequest;
import com.commentdiary.src.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static com.commentdiary.common.response.CommonResponseStatus.SUCCESS;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("")
    public CommonResponse<JwtResponse> signUp(@RequestBody SignUpRequest signUpRequest) {
        return new CommonResponse<>(memberService.signUp(signUpRequest));
    }

//    @PostMapping("login")
//    public CommonResponse<JwtResponse> login(@RequestBody LoginRequest loginRequest) {
//
//    }
}
