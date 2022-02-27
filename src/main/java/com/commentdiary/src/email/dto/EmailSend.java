package com.commentdiary.src.email.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EmailSend {
    private String email;
    private String title;
    private String message;
}
