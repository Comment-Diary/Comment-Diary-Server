package com.commentdiary.src.comment.domain;

import com.commentdiary.common.domain.BaseTimeEntity;
import com.commentdiary.src.diary.domain.Diary;
import com.commentdiary.src.member.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Comment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "diary_id")
    private Diary diary;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Lob
    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private boolean isLike;

    @Column(nullable = false)
    private String date;

    @Builder
    public Comment(Member member, Diary diary, String content, String date, boolean isLike) {
        this.member = member;
        this.diary = diary;
        this.content = content;
        this.date = date;
        this.isLike = isLike;
    }

    public boolean getIsLike() {
        return this.isLike;
    }

    public void likeComment() {
        this.isLike = true;
    }
}