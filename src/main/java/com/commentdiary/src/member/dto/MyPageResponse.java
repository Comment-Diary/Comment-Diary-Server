package com.commentdiary.src.member.dto;

import com.commentdiary.src.member.domain.Member;
import com.commentdiary.src.member.domain.enums.LoginType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyPageResponse {
    private String email;
    @Enumerated(EnumType.STRING)
    private LoginType loginType;
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
                .loginType(member.getLoginType())
                .pushYn(member.getPushYn())
                .temperature(Math.round(member.getTemperature() * 10) / 10.0)
                .build();
    }
}
