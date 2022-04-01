package com.commentdiary.src.member.domain;

import com.commentdiary.common.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@NoArgsConstructor
@Table(name = "refresh_token")
@Entity
public class RefreshToken extends BaseTimeEntity {

    @Id
    private String id;
    private String value;
    private String deviceToken;

    @Builder
    public RefreshToken(String id, String value, String deviceToken) {
        this.id = id;
        this.value = value;
        this.deviceToken = deviceToken;
    }

    public RefreshToken updateValue(String token) {
        this.value = token;
        return this;
    }
}