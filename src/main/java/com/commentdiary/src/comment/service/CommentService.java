package com.commentdiary.src.comment.service;

import com.commentdiary.common.exception.CommonException;
import com.commentdiary.jwt.SecurityUtil;
import com.commentdiary.src.comment.domain.Comment;
import com.commentdiary.src.comment.dto.CreateCommentRequest;
import com.commentdiary.src.comment.dto.LikeResponse;
import com.commentdiary.src.comment.dto.MyCommentResponse;
import com.commentdiary.src.comment.repository.CommentRepository;
import com.commentdiary.src.diary.domain.Diary;
import com.commentdiary.src.diary.repository.DiaryRepository;
import com.commentdiary.src.member.domain.Member;
import com.commentdiary.src.member.repository.MemberRepository;
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

    @Transactional
    public void createComment(CreateCommentRequest commentRequest) {
        Member member = getCurrentMemberId();
        Diary diary = diaryRepository.findById(commentRequest.getDiaryId()).orElseThrow(() -> new CommonException(NOT_MATCHED_DIARY));
        commentRepository.save(commentRequest.toEntity(member, diary, commentRequest));
    }

    @Transactional
    public LikeResponse like(long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CommonException(NOT_MATCHED_COMMENT));
        comment.likeComment();
        return LikeResponse.of(comment);
    }

    @Transactional
    public List<MyCommentResponse> getMyComment() {
        Member member = getCurrentMemberId();
        List<Comment> commentList = commentRepository.findAllByMemberId(member.getId());
        return commentList.stream()
                .map(comment -> MyCommentResponse.of(comment))
                .collect(Collectors.toList());
    }

    private Long getMemberId() {
        return SecurityUtil.getCurrentMemberId();
    }

    private Member getCurrentMemberId() {
        return memberRepository.findById(getMemberId())
                .orElseThrow(() -> new CommonException(NOT_FOUND_MEMBER));
    }

}
