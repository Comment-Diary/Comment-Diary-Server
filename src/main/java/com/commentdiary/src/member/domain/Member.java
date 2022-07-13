package com.commentdiary.src.member.domain;

import com.commentdiary.common.domain.BaseTimeEntity;
import com.commentdiary.common.exception.CommonException;
import com.commentdiary.common.exception.ErrorCode;
import com.commentdiary.src.comment.domain.Comment;
import com.commentdiary.src.comment.domain.Comments;
import com.commentdiary.src.member.domain.enums.LoginType;
import com.commentdiary.src.member.domain.enums.MemberStatus;
import com.commentdiary.src.member.domain.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DynamicInsert
@Builder
@Table(name = "members")
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long socialId;

    @Column(length = 45)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private double temperature;

    @Embedded
    private Comments comments = new Comments();


    @Column(columnDefinition = "varchar(1) default 'Y'", nullable = false)
    private char pushYn;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(10) default 'ACTIVE'", nullable = false)
    private MemberStatus status;

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private LoginType loginType;

    public void checkPassword(PasswordEncoder passwordEncoder, String password) {
        if (!passwordEncoder.matches(password, this.password)) {
            throw new CommonException(ErrorCode.INVALID_PASSWORD);
        }
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public void plusTemp(int likeCmt, int totalCmt) {
        this.temperature += (double) likeCmt / totalCmt * 0.8;
    }

    public void minusTemp(long likeCmt, int totalCmt) {
        this.temperature -= (double) likeCmt / totalCmt * 0.8;
    }

    public void changePushStatus() {
        if (this.pushYn == 'Y') {
            this.pushYn = 'N';
            return;
        }
        
        this.pushYn = 'Y';
    }

    public void addPushAgree(char pushYn) {
        this.pushYn = pushYn;
    }

    public void controlTemperature(boolean isLike) {
        int totalCommentCount = comments.totalCount();
        int likeCommentCount = comments.likeCount();

        if (isLike) {
            plusTemp(likeCommentCount, totalCommentCount);
            return;
        }
        minusTemp(likeCommentCount, totalCommentCount);
    }
}
