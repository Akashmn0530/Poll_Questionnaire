package com.example.pollandvote.Voter.notification;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.pollandvote.Admin.FirestoreUtils.ReadData;
import com.example.pollandvote.Admin.Notification.Adapters.NotificationAdapter;
import com.example.pollandvote.Admin.Notification.Notification;
import com.example.pollandvote.Admin.Notification.NotificationActivity;
import com.example.pollandvote.Admin.Notification.NotificationData;
import com.example.pollandvote.Admin.Utils.UniversalImageLoader;
import com.example.pollandvote.Admin.bottom_nav.BottomNavigation;
import com.example.pollandvote.Admin.bottom_nav.TopPopMenu;
import com.example.pollandvote.R;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class UserNotificationActivity extends AppCompatActivity {
    private NotificationAdapter adapter;
    private List<Notification> notificationList;
    BottomNavigationView bottomNavigationView;
    ProgressBar progressBar;
    ImageView topbarImage;
    private SearchView searchView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_notification);
        progressBar = findViewById(R.id.idProgressBar1);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Handle search query submission
                performSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

        topbarImage  = findViewById(R.id.saveChanges);
        UniversalImageLoader.setImage("", topbarImage, null, "");

        //Notification indicator
        BadgeDrawable badgeDrawable = bottomNavigationView.getOrCreateBadge(R.id.navigation_notifications);
        badgeDrawable.setVisible(false);

        //Bottom Nav
        bottomNavigationView.setSelectedItemId(R.id.navigation_notifications);
        BottomNavigation.bottomNavProvider(bottomNavigationView,getApplicationContext());

        //Recycler View
        RecyclerView recyclerView = findViewById(R.id.rv_notification_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Clear all notifications in notification bar
        clearNotifications();

        notificationList = new ArrayList<>();
        adapter = new NotificationAdapter(notificationList,this);
        recyclerView.setAdapter(adapter);

        ReadData readData = new ReadData();
        readData.readRealtimeData(data -> {
            notificationList.clear();
            notificationList.addAll(data);
            adapter.notifyDataSetChanged();
            progressBar.setVisibility(View.GONE);
        });

        topbarImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Log.d(TAG, "onClick: image clicked");
                TopPopMenu.showPopMenu(v, UserNotificationActivity.this);
            }
        });

    }
    //Delete pop up
    @SuppressLint("NotifyDataSetChanged")
    public void showDeletePopup(Notification notification) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Confirmation");
        builder.setMessage("Are you sure you want to delete the notification: " + notification.title + "?");
        builder.setPositiveButton("Delete", (dialog, which) -> {
            // Handle delete action here
            notificationList.remove(notification);
            adapter.notifyDataSetChanged();
            // Delete the document in notifications in firebase and update the fields
        });
        builder.setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void clearNotifications() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();

    }
    private void performSearch(String query) {

    }

}
