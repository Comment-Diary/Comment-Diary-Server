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
public class DiaryResponse {
    private String title;
    private String content;
    private String date;

    public static DiaryResponse of(Diary diary) {
        return DiaryResponse.builder()
                .title(diary.getTitle())
                .content(diary.getContent())
                .date(diary.getDate())
                .build();
    }
}
