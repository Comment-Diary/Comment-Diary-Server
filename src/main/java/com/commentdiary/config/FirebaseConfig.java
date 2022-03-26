package com.commentdiary.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Slf4j
@Configuration
public class FirebaseConfig {

    private static final String FIREBASE_CONFIG_PATH = "firebase/comment=diary-firebase=key.json";


    @PostConstruct
    public void initialize() {
        try {
            FileInputStream serviceAccount = new FileInputStream(FIREBASE_CONFIG_PATH);

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();
            FirebaseApp.initializeApp(options);
        }
        catch (FileNotFoundException e) {
            log.error("Firebase ServiceAccountKey FileNotFoundException" + e.getMessage());
        }
        catch (IOException e) {
            log.error("FirebaseOptions IOException" + e.getMessage());
        }

    }
}