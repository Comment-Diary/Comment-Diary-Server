package com.commentdiary.common.firebase.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class FcmMessage {
    private boolean validate_only;
    private Message message;

    @Getter
    @AllArgsConstructor
    @Builder
    public static class Message {
        private String token;
        private Notification notification;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class Notification {
        private String title;
        private String body;
        private String image;
    }
}