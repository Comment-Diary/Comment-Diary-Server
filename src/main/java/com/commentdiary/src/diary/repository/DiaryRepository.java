package com.commentdiary.src.diary.repository;

import com.commentdiary.src.diary.domain.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
    List<Diary> findByMemberId(long memberId);
    List<Diary> findByMemberIdAndDateContains(long memberId, String date);

    Optional<Diary> findByIdAndMemberId(long diaryId, long memberId);

    void deleteById(Long id);
}
