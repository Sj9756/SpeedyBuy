package com.example.speedybuy.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;

import androidx.core.app.NotificationManagerCompat;

public class Broadcast_dismiss extends BroadcastReceiver {
    private static final int NOTIFICATION_ID=100;

    @Override
    public void onReceive(Context context, Intent intent) {
        if ("YOUR_DISMISS_ACTION".equals(intent.getAction())) {
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            notificationManager.cancel(NOTIFICATION_ID);
        }
    }
}
