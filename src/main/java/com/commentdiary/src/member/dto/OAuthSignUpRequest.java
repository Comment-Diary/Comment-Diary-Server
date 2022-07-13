package com.commentdiary.src.member.dto;

import com.commentdiary.src.member.domain.Member;
import com.commentdiary.src.member.domain.enums.LoginType;
import com.commentdiary.src.member.domain.enums.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Getter
@NoArgsConstructor
public class OAuthSignUpRequest {
    private char pushYn;
}
