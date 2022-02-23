package com.commentdiary.src.member.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    ROLE_USER("일반 유저"),
    ROLE_ADMIN("관리자")
    ;

    private String description;
}
