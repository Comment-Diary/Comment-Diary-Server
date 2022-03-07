package com.commentdiary.src.comment.dto;

import com.commentdiary.src.comment.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {
    private long id;
    private String content;
    private String date;
    private boolean isLike;


    public static CommentResponse of(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .date(comment.getDate())
                .isLike(comment.getIsLike())
                .build();
    }
}
