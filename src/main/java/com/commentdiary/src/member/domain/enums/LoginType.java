package com.commentdiary.src.member.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LoginType {
    KAKAO("카카오"),
    APPLE("애플");

    private final String description;
}
