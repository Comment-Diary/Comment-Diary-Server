package com.commentdiary.src.emailAuth.controller;

import com.commentdiary.common.response.CommonResponse;
import com.commentdiary.src.emailAuth.dto.ConfirmCodeResquest;
import com.commentdiary.src.emailAuth.dto.EmailAddrRequest;
import com.commentdiary.src.emailAuth.dto.EmailSendDto;
import com.commentdiary.src.emailAuth.service.EmailService;
import com.commentdiary.src.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.commentdiary.common.response.CommonResponseStatus.SUCCESS;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/email")
public class EmailController {

    private final EmailService emailService;
    private final MemberRepository memberRepository;

    @PostMapping("")
    public CommonResponse<Void> sendEmail(@RequestBody EmailAddrRequest emailAddrRequest) {
        if (memberRepository.existsByEmail(emailAddrRequest.getEmail())) {
            // TODO: 이메일 중복 처리
        }
        emailService.sendEmail(new EmailSendDto(emailAddrRequest.getEmail(),"회원가입 인증 메일", ""));
        return new CommonResponse<>(SUCCESS);
    }

    @PostMapping("/confirm")
    public CommonResponse<Void> confirmCode(@RequestBody ConfirmCodeResquest confirmCodeResquest) {
        // TODO: 예외 처리
        if (emailService.confirmCode(confirmCodeResquest)) {
            return new CommonResponse<>(SUCCESS);
        }
        else
            return new CommonResponse<>(SUCCESS);
    }




}