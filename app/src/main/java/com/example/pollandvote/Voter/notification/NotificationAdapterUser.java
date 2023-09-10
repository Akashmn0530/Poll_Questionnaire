package com.example.pollandvote.Voter.notification;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pollandvote.Admin.Notification.Notification;
import com.example.pollandvote.Admin.Notification.NotificationActivity;
import com.example.pollandvote.R;
import com.example.pollandvote.Voter.notification.UserNotificationActivity;

import java.util.List;

public class NotificationAdapterUser extends RecyclerView.Adapter<NotificationAdapterUser.NotificationViewHolder> {
    private static List<Notification> notificationList;
    @SuppressLint("StaticFieldLeak")
    static Context context;

    public NotificationAdapterUser(List<Notification> notificationList, Context context) {
        this.notificationList = notificationList;
        this.context = context;
    }


    @NonNull
    @Override
    public NotificationAdapterUser.NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        return new NotificationAdapterUser.NotificationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapterUser.NotificationViewHolder holder, int position) {
        Notification notification = notificationList.get(position);
        holder.titleTextView.setText(notification.title);
        holder.messageTextView.setText(notification.message);
        holder.timestampTextView.setText(notification.timestamp);
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public static class NotificationViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, messageTextView, timestampTextView;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            messageTextView = itemView.findViewById(R.id.messageTextView);
            timestampTextView = itemView.findViewById(R.id.timestampTextView);
            // Set an OnClickListener to handle item clicks in the RecyclerView
            itemView.setOnClickListener(view -> {
                // Get the clicked contest data
                Notification notification = notificationList.get(getAdapterPosition());
                // Cast the context to AdminHomeActivity and call the showDeletePopup method
                UserNotificationActivity notificationActivity = (UserNotificationActivity) context;
                notificationActivity.showDeletePopup(notification);

            });
        }
    }
}


