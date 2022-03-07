package com.commentdiary.src.report.domain;

import com.commentdiary.common.domain.BaseTimeEntity;
import com.commentdiary.src.comment.domain.Comment;
import com.commentdiary.src.member.domain.Member;
import com.commentdiary.src.member.domain.enums.MemberStatus;
import com.commentdiary.src.report.domain.enums.ReportStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DynamicInsert
@Builder
public class CommentReport extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @ManyToOne
    @JoinColumn(name = "reporter_id")
    private Member reporter;

    @ManyToOne
    @JoinColumn(name = "reported_id")
    private Member reported;

    private String content;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(10) default 'WAITING'", nullable = false)
    private ReportStatus status;

}
