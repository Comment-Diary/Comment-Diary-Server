package com.commentdiary.src.delivery.dto;

import com.commentdiary.src.comment.dto.MyCommentResponse;
import com.commentdiary.src.diary.domain.Diary;
import com.commentdiary.src.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryResponse {
    private long diaryId;
    private String title;
    private String content;
    private String date;
    private List<MyCommentResponse> myCommentResponse;

    public static DeliveryResponse of(Diary diary, Member member) {
        return DeliveryResponse.builder()
                .diaryId(diary.getId())
                .title(diary.getTitle())
                .content(diary.getContent())
                .date(diary.getDate())
                .myCommentResponse(diary.getComments()
                        .stream()
                        .filter(comment -> comment.getMember().equals(member))
                        .map(comment -> MyCommentResponse.of(comment))
                        .collect(Collectors.toList())
                )
                .build();
    }
}
