package com.commentdiary.src.diary.dto;

import com.commentdiary.src.comment.domain.enums.CommentStatus;
import com.commentdiary.src.comment.dto.CommentResponse;
import com.commentdiary.src.diary.domain.Diary;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class DiaryDetailResponse {
    private long id;
    private String title;
    private String content;
    private String date;
    private char deliveryYn;
    private char tempYn;
    private int commentCnt;
    private List<CommentResponse> commentResponseList;

    @Builder
    public DiaryDetailResponse(long id, String title, String content, String date, char deliveryYn, char tempYn, List<CommentResponse> commentResponseList) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.date = date;
        this.deliveryYn = deliveryYn;
        this.tempYn = tempYn;
        this.commentCnt = commentResponseList.size();
        this.commentResponseList = commentResponseList;
    }

    public static DiaryDetailResponse of(Diary diary) {
        return DiaryDetailResponse.builder()
                .id(diary.getId())
                .title(diary.getTitle())
                .content(diary.getContent())
                .date(diary.getDate())
                .deliveryYn(diary.getDeliveryYn())
                .tempYn(diary.getTempYn())
                .commentResponseList(diary.getComments()
                        .stream()
                        .filter(comment -> comment.getStatus().equals(CommentStatus.ACTIVE))
                        .map(comment -> CommentResponse.of(comment))
                        .collect(Collectors.toList()))
                .build();
    }

}
