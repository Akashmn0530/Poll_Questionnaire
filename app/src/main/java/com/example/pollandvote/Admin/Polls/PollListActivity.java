package com.example.pollandvote.Admin.Polls;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pollandvote.Admin.Utils.UniversalImageLoader;
import com.example.pollandvote.Admin.bottom_nav.BottomNavigation;
import com.example.pollandvote.Admin.bottom_nav.TopPopMenu;
import com.example.pollandvote.R;
import com.example.pollandvote.models.PollData;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

public class PollListActivity extends AppCompatActivity {
    private static final String TAG = "PollListActivity";
    private RecyclerView recyclerView;
    private DatabaseReference reference;
    BottomNavigationView bottomNavigationView;
    ImageView topBarImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poll_list);
        initImageLoader();
        // Bottom layout
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.navigation_dashboard);
        BottomNavigation.bottomNavProvider(bottomNavigationView,getApplicationContext());

        topBarImage  = findViewById(R.id.saveChanges);
        UniversalImageLoader.setImage("", topBarImage, null, "");

        recyclerView = findViewById(R.id.recyclerView);
        reference = FirebaseDatabase.getInstance().getReference("AdminPollData");

        // Fetch data from Firebase and display in RecyclerView
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<PollData> pollDataList = new ArrayList<>();
                for (DataSnapshot pollSnapshot : dataSnapshot.getChildren()) {
                    PollData pollData = pollSnapshot.getValue(PollData.class);
                    pollDataList.add(pollData);
                }

                PollDataAdapter adapter = new PollDataAdapter(pollDataList, getApplicationContext());
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        topBarImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Log.d(TAG, "onClick: image clicked");
                TopPopMenu.showPopMenu(v, PollListActivity.this);
            }
        });
    }
    private void initImageLoader(){
        UniversalImageLoader universalImageLoader = new UniversalImageLoader(this);
        ImageLoader.getInstance().init(universalImageLoader.getConfig());
    }
}
