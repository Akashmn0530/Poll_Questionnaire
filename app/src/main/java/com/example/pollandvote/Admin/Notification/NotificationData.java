package com.example.pollandvote.Admin.Notification;

import com.example.pollandvote.Admin.FirestoreUtils.WriteData;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NotificationData {
    public static void storeNotification(String title, String message) {
        WriteData writeData = new WriteData(); //To firebase
        writeData.writeRealtimeData("notifications", title, message, getCurrentTimestamp());
    }

    private static String getCurrentTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date());
    }
}
