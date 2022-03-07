package com.commentdiary.src.member.dto;

import com.commentdiary.src.member.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
public class MyPageResponse {
    private String email;
    private double temp;

    public MyPageResponse(String email, double temp) {
        this.email = email;
        this.temp = temp;
    }

    public static MyPageResponse of(Member member) {
        return MyPageResponse.builder()
                .email(member.getEmail())
                .temp(member.getTemp())
                .build();
    }
}
