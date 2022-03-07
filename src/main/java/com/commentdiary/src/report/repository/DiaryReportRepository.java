package com.commentdiary.src.report.repository;

import com.commentdiary.src.report.domain.DiaryReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryReportRepository extends JpaRepository<DiaryReport, Long> {
}
