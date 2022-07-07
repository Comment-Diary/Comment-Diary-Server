package com.commentdiary.src.member.dto;

import com.commentdiary.src.member.domain.Member;
import com.commentdiary.src.member.domain.enums.LoginType;
import com.commentdiary.src.member.domain.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthLoginRequest {
    @NotNull
    @Enumerated(EnumType.STRING)
    private LoginType loginType;

    private String accessToken;

    private String deviceToken;

    public Member toEntity(Long socialId, LoginType loginType) {
        return Member.builder()
                .socialId(socialId)
                .loginType(loginType)
                .email(String.valueOf(socialId))
                .password(new BCryptPasswordEncoder().encode(String.valueOf(socialId)))
                .pushYn('Y')
                .temperature(36.5)
                .role(Role.ROLE_USER)
                .build();    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(loginType, accessToken);
    }
}
