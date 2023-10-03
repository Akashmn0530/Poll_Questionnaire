package com.example.pollandvote.Voter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;

import com.example.pollandvote.Admin.Utils.UniversalImageLoader;
import com.example.pollandvote.Admin.homescreen.AdminHomeViewFragment;
import com.example.pollandvote.R;
import com.example.pollandvote.Voter.bottom_nav.UserBottomNavigation;
import com.example.pollandvote.Voter.bottom_nav.UserTopPopMenu;
import com.example.pollandvote.Voter.notification.UserNotificationActivity;
import com.example.pollandvote.chatbot.ChatBotActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nostra13.universalimageloader.core.ImageLoader;

public class VoterHome extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    ImageView topBarImage;
    LinearLayout sidePollImage, sideQuestionsImage, sideChatBotImage;
    TextView titleFragment;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voter_home);
        initImageLoader();

        // Bottom layout
        bottomNavigationView = findViewById(R.id.bottomNavigationViewUser);
        bottomNavigationView.setSelectedItemId(R.id.navigation_home_user);
        UserBottomNavigation.bottomNavProvider(
                        bottomNavigationView,
                        getApplicationContext(),
                        getWindow().getDecorView().getRootView());

        topBarImage = findViewById(R.id.saveChanges);
        UniversalImageLoader.setImage("", topBarImage, null, "");

        //Notifications...
        findViewById(R.id.userNotifications).setOnClickListener(view ->this.startActivity(new Intent(this, UserNotificationActivity.class)));

        //side button handling...
        titleFragment = findViewById(R.id.fragmentTitleTextView);
        sidePollImage = findViewById(R.id.linearPoll);
        sideQuestionsImage = findViewById(R.id.linearQuestion);
        sideChatBotImage = findViewById(R.id.linearChat);

        sideChatBotImage.setOnClickListener(v -> {
            sideChatBotImage.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.focus));
            sideQuestionsImage.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nofocus));
            sidePollImage.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nofocus));
            Intent intent = new Intent(getApplicationContext(), ChatBotActivity.class);
            startActivity(intent);
        });
        sideQuestionsImage.setOnClickListener(v -> {
            sideQuestionsImage.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.focus));
            sideChatBotImage.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nofocus));
            sidePollImage.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nofocus));
            titleFragment.setText("Questions list");
            AdminHomeViewFragment adminHomeViewFragment = new AdminHomeViewFragment();
            loadFragment(adminHomeViewFragment);
        });
        sidePollImage.setOnClickListener(v -> {
            sidePollImage.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.focus));
            sideQuestionsImage.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nofocus));
            sideChatBotImage.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nofocus));
        });
        topBarImage.setOnClickListener(v -> {
            UserTopPopMenu.showPopMenu(v, VoterHome.this);
        });

    }

    private void initImageLoader() {
        UniversalImageLoader universalImageLoader = new UniversalImageLoader(this);
        ImageLoader.getInstance().init(universalImageLoader.getConfig());
    }

    public void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.adminHomeContainerFragment, fragment)
                .addToBackStack(null)
                .commit();
    }
}