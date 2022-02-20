package com.commentdiary.src.member.domain;

import com.commentdiary.common.domain.BaseTimeEntity;
import com.commentdiary.src.member.domain.enums.MemberStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "members")
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 45, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

//    @Column(nullable = false)
//    private char pushYn;
//
//    @Enumerated(EnumType.STRING)
//    @Column(columnDefinition = "varchar(10) default 'ACTIVE'", nullable = false)
//    private MemberStatus status;

    @Builder
    public Member (String email, String password) {
        this.email = email;
        this.password = password;
    }
}
