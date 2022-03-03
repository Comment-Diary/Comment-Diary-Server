package com.commentdiary.src.comment.dto;

import com.commentdiary.src.comment.domain.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateCommentResponse {

    private long id;
    private String content;
    private String date;
    private boolean isLike;

    @Builder
    public CreateCommentResponse(long id, String content, String date, boolean isLike) {
        this.id = id;
        this.content = content;
        this.date = date;
        this.isLike = isLike;
    }

    public static CreateCommentResponse of(Comment comment) {
        return CreateCommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .date(comment.getDate())
                .isLike(comment.getIsLike())
                .build();
    }
}
