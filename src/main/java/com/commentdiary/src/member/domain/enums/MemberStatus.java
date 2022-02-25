package com.commentdiary.src.member.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberStatus {
    ACTIVE("활성"),
    DEACTIVE("비활성")
    ;

    private String description;
}
