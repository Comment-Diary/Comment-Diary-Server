package com.commentdiary.src.report.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReportStatus {
    WAITING("처리 중"),
    DONE("완료");

    private String description;
}
