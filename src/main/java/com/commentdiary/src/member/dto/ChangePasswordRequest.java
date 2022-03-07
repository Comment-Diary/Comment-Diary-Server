package com.commentdiary.src.member.dto;

import com.commentdiary.common.exception.CommonException;
import com.commentdiary.common.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChangePasswordRequest {
    @NotBlank(message = "비밀번호를 입력해 주세요.")
    @Size(min = 8, max = 20,message = "비밀번호는 8자 이상, 20자 이하입니다.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$", message = "비밀번호는 숫자, 영어, 특수 문자를 포함해야 합니다.")
    private String password;

    @NotBlank(message = "비밀번호를 입력해 주세요.")
    private String checkPassword;

    public boolean isSamePassword() {
        if (!this.password.equals(this.checkPassword)) {
            throw new CommonException(ErrorCode.INVALID_PASSWORD);
        }
        return true;
    }

}
