package com.commentdiary.src.comment.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommentStatus {
    ACTIVE("활성"),
    BLOCKED("차단")
    ;

    private String description;
}
