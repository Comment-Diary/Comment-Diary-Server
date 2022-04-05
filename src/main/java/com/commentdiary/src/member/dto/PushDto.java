package com.commentdiary.src.member.dto;

import com.commentdiary.src.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PushDto {
    private char pushYn;

    public static PushDto of (Member member) {
        return PushDto.builder()
                .pushYn(member.getPushYn())
                .build();
    }
}
