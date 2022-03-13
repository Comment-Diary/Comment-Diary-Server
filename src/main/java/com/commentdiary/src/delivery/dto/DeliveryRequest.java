package com.commentdiary.src.delivery.dto;

import com.commentdiary.src.delivery.domain.Delivery;
import com.commentdiary.src.diary.domain.Diary;
import com.commentdiary.src.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryRequest {
    private Member receiver;
    private Diary diary;
    private String date;

    public Delivery toEntity(Member receiver, Diary diary, String date) {
        return Delivery.builder()
                .receiver(receiver)
                .diary(diary)
                .date(date)
                .build();
    }
}
