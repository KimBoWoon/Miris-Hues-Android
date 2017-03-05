package com.hues.miris_hues_android.bot;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.hues.miris_hues_android.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;

public class BotActivity extends BaseActivity implements BotContract.UserAction {
    @Bind(R.id.intro)
    Button intro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        bookmarkNoti();
    }

    public void bookmarkNoti() {
        NotificationManager notificationManager = (NotificationManager) BotActivity.this.getSystemService(BotActivity.this.NOTIFICATION_SERVICE);
        Intent intent1 = new Intent(BotActivity.this.getApplicationContext(), BotActivity.class);

        Notification.Builder builder = new Notification.Builder(getApplicationContext());
        intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingNotificationIntent = PendingIntent.getActivity(BotActivity.this, 0, intent1, FLAG_UPDATE_CURRENT);
        builder.setSmallIcon(R.drawable.miris_logo).setTicker("HETT").setWhen(System.currentTimeMillis())
                .setNumber(1).setContentTitle("새로운 도서 도착").setContentText("마지막 잎새")
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE).setContentIntent(pendingNotificationIntent).setAutoCancel(true).setOngoing(true);

        notificationManager.notify(1, builder.build());
    }

    @OnClick(R.id.intro)
    public void Intro() {
        Intent i = new Intent(getApplicationContext(), ChatActivity.class);
        startActivity(i);
    }
}
