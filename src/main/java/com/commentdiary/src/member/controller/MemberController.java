package com.commentdiary.src.member.controller;

import com.commentdiary.common.response.CommonResponse;
import com.commentdiary.src.member.dto.JwtResponse;
import com.commentdiary.src.member.dto.SignUpRequest;
import com.commentdiary.src.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;
    @PostMapping("")
    public CommonResponse<JwtResponse> signUp(@RequestBody SignUpRequest signUpRequest) {
        // TODO: 이메일 형식 체크

        // TODO: 비밀번호 형식 체크

        return new CommonResponse<>(memberService.signUp(signUpRequest));
    }
}
