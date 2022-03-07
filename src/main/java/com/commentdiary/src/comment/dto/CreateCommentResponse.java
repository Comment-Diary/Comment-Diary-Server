package com.commentdiary.src.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCommentResponse {
    private String content;
    private String date;

    public static CreateCommentResponse of (String content, String date) {
        return CreateCommentResponse.builder()
                .content(content)
                .date(date)
                .build();
    }

}
