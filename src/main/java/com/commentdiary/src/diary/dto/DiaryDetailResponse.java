package com.commentdiary.src.diary.dto;

import com.commentdiary.src.comment.dto.CommentResponse;
import com.commentdiary.src.diary.domain.Diary;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor
public class DiaryDetailResponse {
    private long id;
    private String title;
    private String content;
    private String date;
    private char deliveryYn;
    private List<CommentResponse> commentResponseList;

    public DiaryDetailResponse(long id, String title, String content, String date, char deliveryYn, List<CommentResponse> commentResponseList){
        this.id = id;
        this.title = title;
        this.content = content;
        this.date = date;
        this.deliveryYn = deliveryYn;
        this.commentResponseList = commentResponseList;
    }

    public static DiaryDetailResponse of(Diary diary) {
        return DiaryDetailResponse.builder()
                .id(diary.getId())
                .title(diary.getTitle())
                .content(diary.getContent())
                .date(diary.getDate())
                .deliveryYn(diary.getDeliveryYn())
                .commentResponseList(diary.getComments()
                        .stream()
                        .map(comment -> CommentResponse.of(comment))
                        .collect(Collectors.toList()))
                .build();
    }

}
