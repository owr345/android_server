package org.uasecurity.banking.newminingwaking;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.InputStream;

@SpringBootApplication
@EnableScheduling
public class NewMiningWakingApplication {

    public static void main(String[] args) {
        startFirebaseAdmin();
        SpringApplication.run(NewMiningWakingApplication.class, args);
    }

    private static void startFirebaseAdmin() {
        try (InputStream credentialIn =
                     NewMiningWakingApplication.class.getResourceAsStream("/etc/secrets/firebase_key.json")) {
            if (credentialIn == null) {
                throw new RuntimeException("can't find server credential for firebase");
            }

            FirebaseOptions options =
                    FirebaseOptions.builder()
                            .setCredentials(GoogleCredentials.fromStream(credentialIn))
                            .build();
            FirebaseApp.initializeApp(options);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
