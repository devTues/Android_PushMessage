package com.example.and0310_pushmessage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnPush = findViewById(R.id.btnPush);
        TextView tvToken = findViewById(R.id.tvToken);
        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(new OnSuccessListener<String>() {
            @Override
            public void onSuccess(String token) {
                Log.d("token", token);
                tvToken.setText(token);

            }
        });

        btnPush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationManagerCompat notiManager =
                        NotificationManagerCompat.from(getApplicationContext());
                NotificationChannel channel = new NotificationChannel
                        ("testChannel",
                        "TESTChannelName",
                        NotificationManager.IMPORTANCE_DEFAULT);

                notiManager.createNotificationChannel(channel);

                Intent intent = new Intent(MainActivity.this, MainActivity.class);

                PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, 0);
                NotificationCompat.Builder builder =
                        new NotificationCompat.Builder(MainActivity.this, "TestChannelId")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("알림")
                        .setContentText("알림내용")
                        .setContentIntent(pendingIntent);

                notiManager.notify(1, builder.build());


            }
        });
    }
}