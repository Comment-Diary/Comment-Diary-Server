package com.commentdiary.src.report.controller;

import com.commentdiary.common.response.CommonResponse;
import com.commentdiary.src.report.dto.CommentReportRequest;
import com.commentdiary.src.report.dto.DiaryReportRequest;
import com.commentdiary.src.report.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.commentdiary.common.response.CommonResponseStatus.SUCCESS;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/report")
public class ReportRestController {

    private final ReportService reportService;

    @PostMapping("/diary")
    public CommonResponse<Void> diaryReport(@RequestBody DiaryReportRequest diaryReportRequest) {
        reportService.diaryReport(diaryReportRequest);
        return new CommonResponse<>(SUCCESS);
    }

    @PostMapping("/comment")
    public CommonResponse<Void> commentReport(@RequestBody CommentReportRequest commentReportRequest) {
        reportService.commentReport(commentReportRequest);
        return new CommonResponse<>(SUCCESS);
    }
}
