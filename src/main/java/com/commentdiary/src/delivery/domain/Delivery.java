package com.commentdiary.src.delivery.domain;

import com.commentdiary.common.domain.BaseTimeEntity;
import com.commentdiary.src.delivery.domain.enums.DeliveryStatus;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private Member receiver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id")
    private Diary diary;

    private String date;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(10) default 'ACTIVE'", nullable = false)
    private DeliveryStatus status;

    public void blockedDiary() { this.status = DeliveryStatus.BLOCKED; }
}
