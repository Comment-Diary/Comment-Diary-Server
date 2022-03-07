package com.commentdiary.src.report.service;

import com.commentdiary.common.exception.CommonException;
import com.commentdiary.jwt.SecurityUtil;
import com.commentdiary.src.comment.domain.Comment;
import com.commentdiary.src.comment.repository.CommentRepository;
import com.commentdiary.src.diary.domain.Diary;
import com.commentdiary.src.diary.repository.DiaryRepository;
import com.commentdiary.src.member.domain.Member;
import com.commentdiary.src.member.repository.MemberRepository;
import com.commentdiary.src.report.dto.CommentReportRequest;
import com.commentdiary.src.report.dto.DiaryReportRequest;
import com.commentdiary.src.report.repository.CommentReportRepository;
import com.commentdiary.src.report.repository.DiaryReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.commentdiary.common.exception.ErrorCode.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReportService {

    private final CommentReportRepository commentReportRepository;
    private final DiaryReportRepository diaryReportRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final DiaryRepository diaryRepository;

    @Transactional
    public void diaryReport(DiaryReportRequest diaryReportRequest) {
        Member reporter = getMyMember();
        Diary diary = diaryRepository.findById(diaryReportRequest.getDiaryId()).orElseThrow(() -> new CommonException(NOT_FOUND_DIARY));
        Member reported = memberRepository.findById(diary.getMember().getId()).orElseThrow(() -> new CommonException(NOT_FOUND_MEMBER));
        diaryReportRepository.save(diaryReportRequest.toEntity(reporter, reported, diary));
    }

    @Transactional
    public void commentReport(CommentReportRequest commentReportRequest) {
        Member reporter = getMyMember();
        Comment comment = commentRepository.findById(commentReportRequest.getCommentId()).orElseThrow(() -> new CommonException(NOT_MATCHED_COMMENT));
        comment.blockedComment();
        Member reported = memberRepository.findById(comment.getMember().getId()).orElseThrow(() -> new CommonException(NOT_FOUND_MEMBER));
        commentReportRepository.save(commentReportRequest.toEntity(reporter, reported, comment));
    }

    private Long getMemberId() {
        return SecurityUtil.getCurrentMemberId();
    }

    private Member getMyMember() {
        return memberRepository.findById(getMemberId())
                .orElseThrow(() -> new CommonException(NOT_FOUND_MEMBER));
    }
}
