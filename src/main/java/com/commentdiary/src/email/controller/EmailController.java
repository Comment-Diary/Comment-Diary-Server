package com.commentdiary.src.email.controller;

import com.commentdiary.common.exception.CommonException;
import com.commentdiary.common.response.CommonResponse;
import com.commentdiary.src.email.dto.ConfirmCodeResquest;
import com.commentdiary.src.email.dto.EmailAddrRequest;
import com.commentdiary.src.email.dto.EmailSendDto;
import com.commentdiary.src.email.service.EmailService;
import com.commentdiary.src.member.dto.ChangePasswordRequest;
import com.commentdiary.src.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.commentdiary.common.response.CommonResponseStatus.SUCCESS;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/email")
public class EmailController {

    private final EmailService emailService;

    @GetMapping("")
    public CommonResponse<Void> sendCode(@RequestParam(value = "email") EmailAddrRequest emailAddrRequest) {
        emailService.sendCode(new EmailSendDto(emailAddrRequest.getEmail(),"회원가입 인증 메일", ""));
        return new CommonResponse<>(SUCCESS);
    }

    @PostMapping("/confirm")
    public CommonResponse<Void> confirmCode(@RequestBody ConfirmCodeResquest confirmCodeResquest) {
        emailService.confirmCode(confirmCodeResquest);
        return new CommonResponse<>(SUCCESS);
    }

    @GetMapping("/password")
    public CommonResponse<Void> sendPassword(@RequestParam(value = "email") EmailAddrRequest emailAddrRequest) {
        emailService.sendPassword(new EmailSendDto(emailAddrRequest.getEmail(),"임시 비밀번호 발급", ""));
        return new CommonResponse<>(SUCCESS);
    }



}