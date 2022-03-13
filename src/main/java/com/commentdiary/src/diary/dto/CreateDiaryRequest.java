package com.commentdiary.src.diary.dto;

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
public class CreateDiaryRequest {
    private String title;
    private String content;
    private String date;
    private char deliveryYn;
    private char tempYn;

    public Diary toEntity(Member member, CreateDiaryRequest request) {
        return Diary.builder()
                .member(member)
                .title(request.getTitle())
                .content(request.getContent())
                .date(request.getDate())
                .deliveryYn(request.getDeliveryYn())
                .tempYn(request.getTempYn())
                .build();
    }


}
