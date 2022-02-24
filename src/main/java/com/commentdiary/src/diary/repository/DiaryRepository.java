package com.commentdiary.src.diary.repository;

import com.commentdiary.src.diary.domain.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
    List<Diary> findByMemberId(long memberId);
    List<Diary> findByMemberIdAndDateContains(long memberId, String date);
}
