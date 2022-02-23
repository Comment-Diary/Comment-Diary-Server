package com.commentdiary.src.emailAuth.dto;

import com.commentdiary.src.emailAuth.domain.EmailAuth;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ConfirmCodeResquest {
    private String email;
    private int code;
}