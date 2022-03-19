package com.commentdiary.src.diary.repository;

import com.commentdiary.src.diary.domain.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
    List<Diary> findByMemberIdOrderByDateAsc(long memberId);
    List<Diary> findByMemberIdAndDateContainsOrderByDateAsc(long memberId, String date);

    Optional<Diary> findByIdAndMemberIdOrderByDateAsc(long diaryId, long memberId);

    void deleteById(Long id);

    List<Diary> findAllByDeliveryYnIsAndDateContainsAndMemberIsNotNull(char deliveryYn, String date);
}
