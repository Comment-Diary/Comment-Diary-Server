package com.commentdiary.src.comment.repository;

import com.commentdiary.src.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByDiaryId(long diaryId);
    List<Comment> findAllByMemberId(long memberId);
}
