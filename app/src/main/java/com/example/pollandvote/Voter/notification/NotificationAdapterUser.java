package com.example.pollandvote.Voter.notification;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pollandvote.Admin.Notification.Notification;
import com.example.pollandvote.R;

import java.util.ArrayList;
import java.util.List;

public class NotificationAdapterUser extends RecyclerView.Adapter<NotificationAdapterUser.NotificationViewHolder> implements Filterable {
    private final List<Notification> notificationList;
    private final List<Notification> filteredList;
    private final Context context;

    public NotificationAdapterUser(List<Notification> notificationList, Context context) {
        this.notificationList = notificationList;
        this.filteredList = new ArrayList<>(notificationList); // Initialize filteredList with all items
        this.context = context;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        return new NotificationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        Notification notification = filteredList.get(position);
        holder.titleTextView.setText(notification.title);
        holder.messageTextView.setText(notification.message);
        holder.timestampTextView.setText(notification.timestamp);
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String query = constraint.toString().toLowerCase();
                List<Notification> filteredResults = new ArrayList<>();
                if (query.isEmpty()) {
                    filteredResults.addAll(notificationList); // Show all items when query is empty
                } else {
                    for (Notification notification : notificationList) {
                        if (notification.title.toLowerCase().contains(query)) {
                            filteredResults.add(notification);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filteredResults;
                return results;
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredList.clear();
                filteredList.addAll((List<Notification>) results.values);
                notifyDataSetChanged();
            }
        };
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, messageTextView, timestampTextView;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            messageTextView = itemView.findViewById(R.id.messageTextView);
            timestampTextView = itemView.findViewById(R.id.timestampTextView);

            itemView.setOnClickListener(view -> {
                // Get the clicked notification data
                Notification notification = filteredList.get(getAdapterPosition());
                // Cast the context to UserNotificationActivity and call the showDeletePopup method
                UserNotificationActivity notificationActivity = (UserNotificationActivity) context;
                notificationActivity.showDeletePopup(notification);
            });
        }
    }
}
