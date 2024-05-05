package com.example.speedybuy.services;

import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.speedybuy.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FirebaseService extends FirebaseMessagingService  {
    private static final String CHANNEL_ID="1";
    private static final int NOTIFICATION_ID=100;
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (!remoteMessage.getData().isEmpty()) {
            Log.d("ss", "Message data payload: " + remoteMessage.getData());
            String title = remoteMessage.getData().get("title");
            String subtitle = remoteMessage.getData().get("subtitle");
            Uri imageUri=Uri.parse(remoteMessage.getData().get("imageUrl"));
            pushNotification(title,subtitle,imageUri);

        }
    }

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
    }

    private void pushNotification(String title, String subtitle, Uri imageUri) {
        Intent dismissIntent = new Intent(this, Broadcast_dismiss.class);
        dismissIntent.setAction("YOUR_DISMISS_ACTION");
        PendingIntent pendingDismissIntent = PendingIntent.getBroadcast(this, 0, dismissIntent, PendingIntent.FLAG_MUTABLE);
        new Thread(() -> {
            try {
                Bitmap bitmap = loadBitmapFromUrl(imageUri);
                if (bitmap != null) {
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                            .setSmallIcon(R.drawable.app_icon)
                            .setContentTitle(title)
                            .setContentText(subtitle)
                            .setOngoing(true)
                            .addAction(R.drawable.ic_arrow_up_to,"Dismiss",pendingDismissIntent).setColor(getColor(R.color.colorPrimary))
                            .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap))
                            .setPriority(NotificationCompat.PRIORITY_HIGH);
                    NotificationManager notificationManager = createNotificationChannel();
                    assert notificationManager != null;
                    notificationManager.notify(NOTIFICATION_ID, builder.build());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private Bitmap loadBitmapFromUrl(Uri imageUrl) throws IOException {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(imageUrl.toString());
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private NotificationManager createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            NotificationChannelGroup group = new NotificationChannelGroup("44", "event");
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannelGroup(group);
            channel.setGroup("44");
            notificationManager.createNotificationChannel(channel);
            return notificationManager;
        }
        return null;
    }

}
