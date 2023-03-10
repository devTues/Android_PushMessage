package com.example.and0310_pushmessage;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.media.session.MediaSession;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

// FirebaseMessagingService 상속 필수!
// ->  클래스 작성 이후 Manifest.xml 에 service 등록 필수
public class PushService extends FirebaseMessagingService {

    public static final String CHANNEL_ID = "channerID";
    public static final String CHANNEL_NAME = "channelName";
    private NotificationManager manager;

    // 새로운 Token이 생성될때마다 호출되는 callback 메서드
    @Override
    public void onNewToken(@NonNull String token) {
        Log.d("onNewToken", token);
        super.onNewToken(token);

    }

    // push를 전달받으면 동작할 메서드
    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);

        String title = message.getNotification().getTitle();
        String body = message.getNotification().getBody();

        Log.d("title", title);
        Log.d("body", body);

        NotificationManagerCompat notiManager =
                NotificationManagerCompat.from(getApplicationContext());

        NotificationCompat.Builder builder = null;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR_0_1) {
            if (notiManager.getNotificationChannel(CHANNEL_ID) == null) {
                NotificationChannel channel = new NotificationChannel(
                        CHANNEL_ID, CHANNEL_NAME,
                        NotificationManager.IMPORTANCE_DEFAULT
                );
                notiManager.createNotificationChannel(channel);
            }
            builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);
        } else {
            builder = new NotificationCompat.Builder(getApplicationContext());
        }
        builder.setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.mipmap.ic_launcher);

        notiManager.notify(1, builder.build());


    }
}