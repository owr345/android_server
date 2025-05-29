package org.uasecurity.banking.newminingwaking;

import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class FcmWakeUpService {
    private static final Logger log = LogManager.getLogger(FcmWakeUpService.class);
    private static final boolean isKeepAliveDisabled;

    static {
        String env = System.getenv("XMR_BACKEND_DISABLE_FCM");
        isKeepAliveDisabled = Boolean.parseBoolean(env);
    }

    @Scheduled(initialDelay = 0, fixedRate = 1, timeUnit = TimeUnit.MINUTES)
    public void wakeUpAllDevice() {
        if (isKeepAliveDisabled) {
            log.debug(
                    "FCM device keep alive disabled, to change the behavior, configure XMR_BACKEND_DISABLE_FCM env variable");
            return;
        }
        broadcastMessageToTopic("all");
        broadcastMessageToTopic("all-2");
        for (int i = 0; i < 10; ++i) {
            broadcastMessageToTopic("topic" + i);
            try {
                Thread.sleep(200);
            } catch (InterruptedException ignored) {

            }
        }
    }

    private void broadcastMessageToTopic(String topic) {
        Message message =
                Message.builder()
                        .putData("data", new Date().toString())
                        .setTopic(topic)
                        .setAndroidConfig(
                                AndroidConfig.builder()
                                        .setPriority(AndroidConfig.Priority.HIGH)
                                        .build())
                        .build();

        try {
            FirebaseMessaging.getInstance().send(message);
            log.info("woke up all devices successfully: topic: {}", topic);
        } catch (FirebaseMessagingException e) {
            log.error("failed to wake devices: FCM error: {}", e.toString());
        }
    }
}
