package com.commentdiary.src.email.service;

import com.commentdiary.common.exception.CommonException;
import com.commentdiary.common.exception.ErrorCode;
import com.commentdiary.src.email.domain.EmailAuth;
import com.commentdiary.src.email.dto.ConfirmCodeResquest;
import com.commentdiary.src.email.dto.EmailSendDto;
import com.commentdiary.src.email.repository.EmailAuthRepository;
import com.commentdiary.src.member.domain.Member;
import com.commentdiary.src.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import static com.commentdiary.common.exception.ErrorCode.*;


@Service
@Transactional
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;
    private final EmailAuthRepository emailAuthRepository;
    private final MemberRepository memberRepository;

    @Async
    public void sendCode(EmailSendDto emailSendDto) {
        if (memberRepository.existsByEmail(emailSendDto.getEmail())) {
            throw new CommonException(DUPLICATED_EMAIL);
        }

        int code = createCode();
        String message = emailContentBuilder(code);

        EmailSendDto temp = new EmailSendDto(emailSendDto.getEmail(), emailSendDto.getTitle(), message);
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            messageHelper.setTo(temp.getEmail());
            messageHelper.setSubject(temp.getTitle());
            messageHelper.setText(temp.getMessage(), true);
        };

        javaMailSender.send(messagePreparator);

        if  (emailAuthRepository.existsByEmail(emailSendDto.getEmail())) {
            EmailAuth emailAuth = emailAuthRepository.findByEmail(emailSendDto.getEmail()).orElseThrow(() -> new IllegalArgumentException("Not Found"));
            emailAuth.updateCode(code);
        }

        else {
            emailAuthRepository.save(EmailAuth.builder()
                    .email(emailSendDto.getEmail())
                    .code(code)
                    .build());
        }

    }

    public boolean confirmCode(ConfirmCodeResquest confirmCodeResquest) {
        if (emailAuthRepository.existsByEmailAndCode(confirmCodeResquest.getEmail(), confirmCodeResquest.getCode())) {
            return true;
        }
        else {
            throw new CommonException(NOT_MATCHED_CODE);
        }
    }

    private String emailContentBuilder(int code) {
        Context context = new Context();
        context.setVariable("code", code);
        return templateEngine.process("email-template", context);
    }


    private int createCode() {
        int code = (int)((Math.random()* (9999 - 1000 + 1)) + 1000);
        return code;
    }

}