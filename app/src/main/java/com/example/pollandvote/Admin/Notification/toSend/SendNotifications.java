package com.example.pollandvote.Admin.Notification.toSend;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.net.Uri;

import androidx.core.app.NotificationCompat;

import com.example.pollandvote.Admin.Notification.NotificationActivity;
import com.example.pollandvote.Admin.Notification.NotificationData;
import com.example.pollandvote.R;
import com.google.android.material.badge.BadgeDrawable;

public class SendNotifications {

    public static final String NOTIFICATION_CHANNEL_ID = "10001";
    private final static String DEFAULT_NOTIFICATION_CHANNEL_ID = "default";

    public static void sendNotificationOperations(Context context) {
        // Use context parameter to getPackageName() and getSystemService()
        Uri sound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + context.getPackageName() + "/raw/quite_impressed.mp3");

        // Create an explicit intent to open the NotificationActivity when the notification is clicked
        Intent intent = new Intent(context, NotificationActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, DEFAULT_NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Test")
                .setSound(sound)
                .setContentText("Hello! This is my first push notification")
                .setContentIntent(pendingIntent) // Set the click action intent
                .setAutoCancel(true); // Automatically dismiss the notification when clicked

        //Call NotificationData class to store all notifications
        NotificationData.storeNotification("Test","Hello! This is my first push notification");

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_ALARM)
                .build();
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(Color.RED);
        notificationChannel.enableVibration(true);
        notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        notificationChannel.setSound(sound, audioAttributes);

        mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
        assert mNotificationManager != null;
        mNotificationManager.createNotificationChannel(notificationChannel);
        mNotificationManager.notify((int) System.currentTimeMillis(), mBuilder.build());
    }

    public static void showNewNotifications(BadgeDrawable badgeDrawable) {
        badgeDrawable.setVisible(true);
    }
}
