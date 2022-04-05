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
    private char pushYn;

    public MyPageResponse(String email, double temperature, char pushYn) {
        this.email = email;
        this.pushYn = pushYn;
        this.temperature = temperature;
    }

    public static MyPageResponse of(Member member) {
        return MyPageResponse.builder()
                .email(member.getEmail())
                .pushYn(member.getPushYn())
                .temperature(Math.round(member.getTemperature() * 10) / 10.0)
                .build();
    }
}
