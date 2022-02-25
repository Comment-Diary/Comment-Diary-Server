package com.commentdiary.src.email.dto;

import com.commentdiary.src.email.domain.EmailAuth;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EmailSendDto {
    private String email;
    private String title;
    private String message;
}
