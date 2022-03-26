//package com.commentdiary.common.firebase.service;
//
//import com.commentdiary.common.exception.CommonException;
//import com.commentdiary.common.firebase.dto.FcmMessage;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import okhttp3.*;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.http.HttpHeaders;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//import java.net.ConnectException;
//import java.util.Arrays;
//
//import static com.commentdiary.common.exception.ErrorCode.FCM_JSON_PARSE_ERROR;
//import static com.commentdiary.common.exception.ErrorCode.FCM_SERVER_ERROR;
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class FcmService {
//    private static final String API_URL = "https://fcm.googleapis.com/v1/projects/comment-diary\n-cafe/messages:send";
//    private static final String MESSAGING_SCOPE = "https://www.googleapis.com/auth/firebase.messaging";
//    private static final String[] SCOPES = {MESSAGING_SCOPE};
//    private static final String FIREBASE_CONFIG_PATH = "firebase/comment=diary-firebase=key.json";
//
//    private final ObjectMapper objectMapper;
//
//    public void sendMessageTo(String targetToken, String title, String body) {
//        try {
//            String message = makeMessage(targetToken, title, body);
//
//            OkHttpClient client = new OkHttpClient();
//            RequestBody requestBody = RequestBody.create(message, MediaType.get("application/json; charset=utf-8"));
//            Request request = new Request.Builder()
//                    .url(API_URL)
//                    .post(requestBody)
//                    .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
//                    .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
//                    .build();
//
//            Response response  = client.newCall(request).execute();
//        } catch (Exception e) {
//            throw new CommonException(FCM_SERVER_ERROR);
//        }
//    }
//
//    private String makeMessage(String targetToken, String title, String body) {
//        try {
//            FcmMessage fcmMessage = FcmMessage.builder()
//                    .message(FcmMessage.Message.builder()
//                            .token(targetToken)
//                            .notification(FcmMessage.Notification.builder()
//                                    .title(title)
//                                    .body(body)
//                                    .image(null)
//                                    .build()
//                            )
//                            .build()
//                    )
//                    .validate_only(false)
//                    .build();
//
//            return objectMapper.writeValueAsString(fcmMessage);
//        } catch (JsonProcessingException e) {
//            throw new CommonException(FCM_JSON_PARSE_ERROR);
//        }
//    }
//
//    private static String getAccessToken() throws IOException {
//        GoogleCredential googleCredential = GoogleCredential
//                .fromStream(new ClassPathResource(FIREBASE_CONFIG_PATH).getInputStream())
//                .createScoped(Arrays.asList(SCOPES));
//        googleCredential.refreshToken();
//        return googleCredential.getAccessToken();
//    }
//}
