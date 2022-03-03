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
public class CreateCommentRequest {
    private long diaryId;
    private String content;
    private String date;

    public Comment toEntity(Member member, Diary diary, CreateCommentRequest createCommentRequest) {
        return Comment.builder()
                .member(member)
                .diary(diary)
                .content(createCommentRequest.content)
                .date(createCommentRequest.date)
                .isLike(false)
                .build();
    }

}
