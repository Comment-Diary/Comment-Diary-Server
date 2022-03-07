package com.commentdiary.src.report.dto;

import com.commentdiary.src.comment.domain.Comment;
import com.commentdiary.src.member.domain.Member;
import com.commentdiary.src.report.domain.CommentReport;
import com.commentdiary.src.report.domain.enums.ReportStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentReportRequest {
    private long commentId;
    private String content;

    public CommentReport toEntity(Member reporter, Member reported, Comment comment) {
        return CommentReport.builder()
                .reporter(reporter)
                .reported(reported)
                .comment(comment)
                .content(content)
                .status(ReportStatus.WAITING)
                .build();

    }
}
