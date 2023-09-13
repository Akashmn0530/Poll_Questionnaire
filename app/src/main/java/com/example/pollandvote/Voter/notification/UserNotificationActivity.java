package com.example.pollandvote.Voter.notification;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pollandvote.Admin.FirestoreUtils.ReadData;
import com.example.pollandvote.Admin.Notification.Notification;
import com.example.pollandvote.Admin.Utils.UniversalImageLoader;
import com.example.pollandvote.Admin.bottom_nav.BottomNavigation;
import com.example.pollandvote.Admin.bottom_nav.TopPopMenu;
import com.example.pollandvote.R;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class UserNotificationActivity extends AppCompatActivity {
    private NotificationAdapterUser adapter;
    private List<Notification> notificationList;
    BottomNavigationView bottomNavigationView;
    ProgressBar progressBar;
    ImageView topbarImage;

    @SuppressLint({"MissingInflatedId", "NotifyDataSetChanged"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_notification);
        progressBar = findViewById(R.id.idProgressBar1);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

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
        adapter = new NotificationAdapterUser(notificationList,this);
        recyclerView.setAdapter(adapter);
        ReadData readData = new ReadData();
        readData.readRealtimeData(data -> {
            notificationList.clear();
            notificationList.addAll(data);
            adapter.getFilter().filter("");
            adapter.notifyDataSetChanged();
            progressBar.setVisibility(View.GONE);
        });

        topbarImage.setOnClickListener(v -> TopPopMenu.showPopMenu(v, UserNotificationActivity.this));
        SearchView searchView = (SearchView) findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        findViewById(R.id.pollSearch).setOnClickListener(view -> adapter.getFilter().filter("Poll"));
        findViewById(R.id.questionsSearch).setOnClickListener(view -> adapter.getFilter().filter("Question"));
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
}
