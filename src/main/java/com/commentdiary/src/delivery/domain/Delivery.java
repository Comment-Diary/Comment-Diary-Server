package com.commentdiary.src.delivery.domain;

import com.commentdiary.common.domain.BaseTimeEntity;
import com.commentdiary.src.diary.domain.Diary;
import com.commentdiary.src.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Entity
@Builder
public class Delivery extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private Member receiver;

    @ManyToOne
    @JoinColumn(name = "diary_id")
    private Diary diary;

    private String date;

    public void deliveryDiary(Member receiver, Diary diary, String date) {
        this.receiver = receiver;
        this.diary = diary;
        this.date = date;
    }
}
