package com.commentdiary.src.member.domain;

import com.commentdiary.common.domain.BaseTimeEntity;
import com.commentdiary.common.exception.CommonException;
import com.commentdiary.common.exception.ErrorCode;
import com.commentdiary.src.member.domain.enums.MemberStatus;
import com.commentdiary.src.member.domain.enums.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

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

    @Enumerated(EnumType.STRING)
    private Role role;

//    @Column(nullable = false)
//    private char pushYn;
//
//    @Enumerated(EnumType.STRING)
//    @Column(columnDefinition = "varchar(10) default 'ACTIVE'", nullable = false)
//    private MemberStatus status;

    @Builder
    public Member (String email, String password, Role role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public void checkPassword(PasswordEncoder passwordEncoder, String password) {
        if (!passwordEncoder.matches(password, this.password)) {
            throw new CommonException(ErrorCode.INVALID_PASSWORD);
        }
    }

    public void changePassword(String password) {
        this.password = password;
    }
}
