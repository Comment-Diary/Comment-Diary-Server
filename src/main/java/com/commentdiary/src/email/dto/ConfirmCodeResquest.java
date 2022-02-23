package com.commentdiary.src.email.dto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ConfirmCodeResquest {
    private String email;
    private int code;
}