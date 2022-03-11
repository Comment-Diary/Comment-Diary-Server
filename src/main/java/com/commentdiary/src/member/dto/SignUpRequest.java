package com.commentdiary.src.member.dto;

import com.commentdiary.common.exception.CommonException;
import com.commentdiary.common.exception.ErrorCode;
import com.commentdiary.src.member.domain.Member;
import com.commentdiary.src.member.domain.enums.Role;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

    @NotBlank(message = "이메일을 입력해 주세요.")
    @Email(message = "올바른 이메일 주소를 입력해 주세요.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해 주세요.")
    @Size(min = 8, max = 20,message = "비밀번호는 8자 이상, 20자 이하입니다.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$", message = "비밀번호는 숫자, 영어, 특수 문자를 포함한 8 ~ 20자여야 합니다.")
    private String password;

    @NotBlank(message = "비밀번호를 입력해 주세요.")
    private String checkPassword;


    public Member toEntity() {
        return Member.builder()
                .email(email)
                .password(new BCryptPasswordEncoder().encode(password))
                .role(Role.ROLE_USER)
                .build();
    }

    public boolean isSamePassword() {
        if (!this.password.equals(this.checkPassword)) {
            throw new CommonException(ErrorCode.INVALID_PASSWORD);
        }
        return true;
    }
}
