package com.commentdiary.src.comment.service;

import com.commentdiary.common.exception.CommonException;
import com.commentdiary.common.firebase.service.FcmService;
import com.commentdiary.jwt.SecurityUtil;
import com.commentdiary.src.comment.domain.Comment;
import com.commentdiary.src.comment.dto.*;
import com.commentdiary.src.comment.repository.CommentRepository;
import com.commentdiary.src.diary.domain.Diary;
import com.commentdiary.src.diary.repository.DiaryRepository;
import com.commentdiary.src.member.domain.Member;
import com.commentdiary.src.member.domain.RefreshToken;
import com.commentdiary.src.member.repository.MemberRepository;
import com.commentdiary.src.member.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.commentdiary.common.exception.ErrorCode.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final DiaryRepository diaryRepository;
    private final FcmService fcmService;
    private final RefreshTokenRepository refreshTokenRepository;


    @Transactional
    public CreateCommentResponse createComment(CreateCommentRequest createCommentRequest) {
        Member member = getMyMember();
        Diary diary = diaryRepository.findById(createCommentRequest.getDiaryId()).orElseThrow(() -> new CommonException(NOT_MATCHED_DIARY));
        commentRepository.save(createCommentRequest.toEntity(member, diary, createCommentRequest));
        notification(diary);
        return CreateCommentResponse.of(createCommentRequest.getContent(), createCommentRequest.getDate());
    }

    @Transactional
    public LikeResponse like(long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CommonException(NOT_MATCHED_COMMENT));
        comment.likeComment();
        comment.controlTemperature();

        return LikeResponse.of(comment);
    }

    @Transactional
    public List<MyCommentResponse> getMyComment() {
        Member member = getMyMember();
        List<Comment> commentList = commentRepository.findAllByMemberIdOrderByDateDesc(member.getId());
        return commentList.stream()
                .map(comment -> MyCommentResponse.of(comment))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<MyCommentResponse> getMyCommentByDate(String date) {
        Member member = getMyMember();
        List<Comment> commentList = commentRepository.findAllByMemberIdAndDateContainsOrderByDateDesc(member.getId(), date);
        return commentList.stream()
                .map(comment -> MyCommentResponse.of(comment))
                .collect(Collectors.toList());
    }

    private void notification(Diary diary) {
        Member member = diary.getMember();

        String title = "코다 - 코멘트 다이어리";
        String body = "코멘트가 도착하였습니다.";

        // 푸시 알림 받을 유저가 로그아웃한 경우
        if (!refreshTokenRepository.existsById(String.valueOf(member.getId()))) {
            return;
        }

        if (member.getPushYn() == 'Y') {
            RefreshToken deviceToken = refreshTokenRepository.findById(String.valueOf(member.getId())).orElseThrow(() -> new CommonException(NOT_FOUND_MEMBER));
            try {
                fcmService.sendMessageTo(deviceToken.getDeviceToken(),
                        title,
                        body);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Long getMemberId() {
        return SecurityUtil.getCurrentMemberId();
    }

    private Member getMyMember() {
        return memberRepository.findById(getMemberId())
                .orElseThrow(() -> new CommonException(NOT_FOUND_MEMBER));
    }

}
