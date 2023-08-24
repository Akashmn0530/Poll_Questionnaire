package com.example.pollandvote.Admin.FirestoreUtils;



import android.util.Log;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class WriteData {
     public void writeRealtimeData(String collectionName, String title, String message, String time){

        // Get an instance of the Realtime Database
         FirebaseDatabase database = FirebaseDatabase.getInstance();
         DatabaseReference notificationsRef = database.getReference(collectionName);
         DatabaseReference newNotificationRef = notificationsRef.push();

         // Create a map with the notification data
         Map<String, Object> notificationData = new HashMap<>();
         notificationData.put("title", title);
         notificationData.put("message", message);
         notificationData.put("timestamp", time);

         // Set the data in the database
         newNotificationRef.setValue(notificationData)
                 .addOnSuccessListener(aVoid -> Log.d("Akash","Success..."))
                 .addOnFailureListener(e -> Log.d("Akash","Failed..."));

     }
}
