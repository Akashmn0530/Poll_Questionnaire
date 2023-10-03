package com.example.pollandvote.Voter.user_poll;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pollandvote.Admin.Utils.UniversalImageLoader;
import com.example.pollandvote.R;
import com.example.pollandvote.Voter.VoterHome;
import com.example.pollandvote.Voter.bottom_nav.UserBottomNavigation;
import com.example.pollandvote.Voter.bottom_nav.UserTopPopMenu;
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

public class UserPollActivity extends AppCompatActivity {

    private static final String TAG = "UserPollActivity";
    private RecyclerView userPollRecyclerView;
    private DatabaseReference reference;

    private Button submitAnswersButton;
    private List<PollData> pollDataList;
    BottomNavigationView bottomNavigationView;
    ImageView topBarImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_poll);
        initImageLoader();

        // Bottom layout
        bottomNavigationView = findViewById(R.id.bottomNavigationViewUser);
        bottomNavigationView.setSelectedItemId(R.id.navigation_poll_user);
        UserBottomNavigation.bottomNavProvider(
                bottomNavigationView,
                getApplicationContext(),
                getWindow().getDecorView().getRootView());

        topBarImage = findViewById(R.id.saveChanges);
        UniversalImageLoader.setImage("", topBarImage, null, "");

        userPollRecyclerView = findViewById(R.id.userPollRecyclerView);
       // submitAnswersButton = findViewById(R.id.submitAnswersButton);

        pollDataList = new ArrayList<>();

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

                OptionAdaptorUser adaptorUser = new OptionAdaptorUser(pollDataList, getApplicationContext());
                userPollRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                userPollRecyclerView.setAdapter(adaptorUser);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
            }
        });
        userPollRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        topBarImage.setOnClickListener(v -> {
            UserTopPopMenu.showPopMenu(v, UserPollActivity.this);
        });
    }
    private void initImageLoader() {
        UniversalImageLoader universalImageLoader = new UniversalImageLoader(this);
        ImageLoader.getInstance().init(universalImageLoader.getConfig());
    }

}
