package com.commentdiary.src.diary.domain;

import com.commentdiary.common.domain.BaseTimeEntity;
import com.commentdiary.src.comment.domain.Comment;
import com.commentdiary.src.member.domain.Member;
import com.commentdiary.src.member.domain.enums.MemberStatus;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Entity
public class Diary extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private String title;

    @Lob
    private String content;

    private String date;

    private char deliveryYn;

    private boolean isDeleted;

    @OneToMany(mappedBy = "diary")
    private List<Comment> comments = new ArrayList<>();



    @Builder
    public Diary(Member member, String title, String content, String date, char deliveryYn, boolean isDeleted) {
        this.member = member;
        this.title = title;
        this.content = content;
        this.date = date;
        this.deliveryYn = deliveryYn;
        this.isDeleted = isDeleted;
    }

    public void updateDiary(String title, String content) {
        this.title = title;
        this.content = content;
    }

}
