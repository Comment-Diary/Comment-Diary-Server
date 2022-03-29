package com.commentdiary.src.comment.domain;

import com.commentdiary.common.domain.BaseTimeEntity;
import com.commentdiary.src.comment.domain.enums.CommentStatus;
import com.commentdiary.src.diary.domain.Diary;
import com.commentdiary.src.member.domain.Member;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Entity
@Builder
public class Comment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id")
    private Diary diary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Lob
    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private boolean isLike;

    @Column(nullable = false)
    private String date;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(10) default 'ACTIVE'", nullable = false)
    private CommentStatus status;

    public boolean getIsLike() {
        return this.isLike;
    }

    public void likeComment() {
        this.isLike = true;
    }

    public void blockedComment() {
        this.status = CommentStatus.BLOCKED;
    }
}