package com.commentdiary.src.comment.dto;

import com.commentdiary.src.comment.domain.Comment;
import com.commentdiary.src.diary.domain.Diary;
import com.commentdiary.src.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequest {
    private long diaryId;
    private String content;

    public Comment toEntity(Member member, Diary diary, CommentRequest commentRequest) {
        return Comment.builder()
                .member(member)
                .diary(diary)
                .content(commentRequest.content)
                .isLike(false)
                .build();
    }

}
