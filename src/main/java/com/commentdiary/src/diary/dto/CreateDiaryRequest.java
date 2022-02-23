package com.commentdiary.src.diary.dto;

import com.commentdiary.src.diary.domain.Diary;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateDiaryRequest {
    private String diaryDate;
    private String content;
    private char deliveryYn;
    
}
