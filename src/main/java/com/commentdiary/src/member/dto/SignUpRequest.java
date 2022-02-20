package com.commentdiary.src.member.dto;

import com.commentdiary.src.member.domain.Member;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Getter
@NoArgsConstructor
public class SignUpRequest {
    private String email;
    private String password;

    @Builder
    public SignUpRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Member toEntity() {
        return Member.builder()
                .email(email)
                .password(new BCryptPasswordEncoder().encode(password))
                .build();
    }
}
