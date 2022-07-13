package com.commentdiary.src.diary.repository;

import com.commentdiary.src.diary.domain.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
    List<Diary> findByMemberIdOrderByDateDesc(long memberId);

    List<Diary> findByMemberIdAndDateContainsOrderByDateDesc(long memberId, String date);

    Optional<Diary> findByIdAndMemberId(long diaryId, long memberId);

    void deleteById(Long id);

    @Query("SELECT d " +
            "FROM Diary d " +
            "WHERE d.deliveryYn = 'Y' " +
            "AND d.date = :date " +
            "AND d.tempYn = 'N' AND d.member IS NOT NULL")
    List<Diary> findAllByDeliveryDiaries(String date);
}
