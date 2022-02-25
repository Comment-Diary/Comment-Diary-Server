package com.commentdiary.src.comment.dto;

import com.commentdiary.src.comment.domain.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentResponse {

    private long id;
    private String content;
    private boolean isLike;

    @Builder
    public CommentResponse(long id, String content, boolean isLike) {
        this.id = id;
        this.content = content;
        this.isLike = isLike;
    }

    public static CommentResponse of(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .isLike(comment.getIsLike())
                .build();
    }
}
