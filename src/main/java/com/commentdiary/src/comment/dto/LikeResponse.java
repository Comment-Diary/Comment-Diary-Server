package com.commentdiary.src.comment.dto;

import com.commentdiary.src.comment.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikeResponse {
    private long id;
    private boolean isLike;

    public static LikeResponse of(Comment comment) {
        return LikeResponse.builder()
                .id(comment.getId())
                .isLike(comment.getIsLike())
                .build();
    }
}