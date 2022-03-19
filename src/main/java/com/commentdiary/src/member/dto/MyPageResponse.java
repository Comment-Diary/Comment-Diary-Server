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
    private double temperature;

    public MyPageResponse(String email, double temperature) {
        this.email = email;
        this.temperature = temperature;
    }

    public static MyPageResponse of(Member member) {
        return MyPageResponse.builder()
                .email(member.getEmail())
                .temperature(member.getTemperature())
                .build();
    }
}
