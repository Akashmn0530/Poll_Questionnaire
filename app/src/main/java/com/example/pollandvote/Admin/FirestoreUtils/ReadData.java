package com.example.pollandvote.Admin.FirestoreUtils;

import androidx.annotation.NonNull;

import com.example.pollandvote.Admin.Notification.Notification;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ReadData {

    public interface OnDataLoadedListener {
        void onDataLoaded(List<Notification> data);
    }

    public void readRealtimeData(OnDataLoadedListener listener) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference notificationsRef = database.getReference("notifications");

        notificationsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Notification> notificationList = new ArrayList<>();
                for (DataSnapshot notificationSnapshot : dataSnapshot.getChildren()) {
                    String title = notificationSnapshot.child("title").getValue(String.class);
                    String message = notificationSnapshot.child("message").getValue(String.class);
                    String timestamp = notificationSnapshot.child("timestamp").getValue(String.class);
                    notificationList.add(new Notification(title, message, timestamp));
                }
                listener.onDataLoaded(notificationList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error here
            }
        });
    }
}
