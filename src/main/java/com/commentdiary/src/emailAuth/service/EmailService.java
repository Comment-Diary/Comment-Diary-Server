package com.commentdiary.src.emailAuth.service;

import com.commentdiary.src.emailAuth.domain.EmailAuth;
import com.commentdiary.src.emailAuth.dto.ConfirmCodeResquest;
import com.commentdiary.src.emailAuth.dto.EmailSendDto;
import com.commentdiary.src.emailAuth.repository.EmailAuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


@Service
@Transactional
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;
    private final EmailAuthRepository emailAuthRepository;

    @Async
    public void sendEmail(EmailSendDto emailSendDto) {
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

    public String emailContentBuilder(int code) {
        Context context = new Context();
        context.setVariable("code", code);

        return templateEngine.process("email-template", context);
    }

    public int createCode() {
        int code = (int)((Math.random()* (9999 - 1000 + 1)) + 1000);
        return code;
    }

    public boolean confirmCode(ConfirmCodeResquest confirmCodeResquest) {
        return emailAuthRepository.existsByEmailAndCode(confirmCodeResquest.getEmail(), confirmCodeResquest.getCode());
    }

}