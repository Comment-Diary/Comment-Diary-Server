package com.commentdiary.src.report.dto;

import com.commentdiary.src.delivery.domain.Delivery;
import com.commentdiary.src.diary.domain.Diary;
import com.commentdiary.src.member.domain.Member;
import com.commentdiary.src.report.domain.DiaryReport;
import com.commentdiary.src.report.domain.enums.ReportStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiaryReportRequest {
    private long diaryId;
    private String content;

    public DiaryReport toEntity(Member reporter, Member reported, Delivery delivery) {
        return DiaryReport.builder()
                .reporter(reporter)
                .reported(reported)
                .diary(delivery.getDiary())
                .content(content)
                .status(ReportStatus.WAITING)
                .build();
    }
}
