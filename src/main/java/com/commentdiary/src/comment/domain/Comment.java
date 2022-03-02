package com.commentdiary.src.comment.domain;

import com.commentdiary.common.domain.BaseTimeEntity;
import com.commentdiary.src.comment.dto.CommentRequest;
import com.commentdiary.src.diary.domain.Diary;
import com.commentdiary.src.member.domain.Member;
import com.commentdiary.src.member.domain.enums.MemberStatus;
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

    @Builder
    public Comment(Member member, Diary diary, String content, boolean isLike) {
        this.member = member;
        this.diary = diary;
        this.content = content;
        this.isLike = isLike;
    }

    public boolean getIsLike() {
        return this.isLike;
    }

    public void likeComment() {
        if (this.getIsLike()) {
            this.isLike = false;
        }
        else {
            this.isLike = true;
        }
    }
}