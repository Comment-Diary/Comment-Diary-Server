package com.commentdiary.src.diary.repository;

import com.commentdiary.src.diary.domain.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
    List<Diary> findByMemberIdOrderByDateDesc(long memberId);

    List<Diary> findByMemberIdAndDateContainsOrderByDateDesc(long memberId, String date);

    //    @Query("SELECT d " +
//            "FROM Diary d " +
//            "WHERE d.id = :diaryId " +
//            "AND d.member.id = :memberId " +
//            "ORDER BY d.date DESC")
    Optional<Diary> findByIdAndMemberIdOrderByDateDesc(long diaryId, long memberId);

    void deleteById(Long id);

    @Query("SELECT d " +
            "FROM Diary d " +
            "WHERE d.deliveryYn = :deliveryYn " +
            "AND d.date = :date " +
            "AND d.tempYn = :tempYn AND d.member IS NOT NULL")
    List<Diary> findAllByDeliveryYnIsAndDateContainsAndMemberIsNotNullAndTempYnEquals(char deliveryYn, String date, char tempYn);
}