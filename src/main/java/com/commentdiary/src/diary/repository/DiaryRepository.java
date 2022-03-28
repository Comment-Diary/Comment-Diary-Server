package com.commentdiary.src.diary.repository;

import com.commentdiary.src.diary.domain.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
    List<Diary> findByMemberIdOrderByDateDesc(long memberId);
    List<Diary> findByMemberIdAndDateContainsOrderByDateDesc(long memberId, String date);

    Optional<Diary> findByIdAndMemberIdOrderByDateDesc(long diaryId, long memberId);

    void deleteById(Long id);

    List<Diary> findAllByDeliveryYnIsAndDateContainsAndMemberIsNotNullAndTempYnEquals(char deliveryYn, String date, char tempYn);
}
