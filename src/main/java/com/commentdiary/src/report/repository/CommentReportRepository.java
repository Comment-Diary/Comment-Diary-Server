package com.commentdiary.src.report.repository;

import com.commentdiary.src.report.domain.CommentReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentReportRepository extends JpaRepository<CommentReport, Long> {
}
