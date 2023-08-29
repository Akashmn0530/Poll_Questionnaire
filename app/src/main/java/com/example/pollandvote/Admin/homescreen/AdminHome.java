package com.example.pollandvote.Admin.homescreen;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.pollandvote.Admin.bottom_nav.BottomNavigation;
import com.example.pollandvote.Admin.bottom_nav.TopPopMenu;
import com.example.pollandvote.R;
import com.example.pollandvote.Admin.Utils.UniversalImageLoader;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nostra13.universalimageloader.core.ImageLoader;


public class AdminHome extends AppCompatActivity{
    BottomNavigationView bottomNavigationView;
    ImageView topbarImage, sidePollImage, sideQuestionsImage, sideChatBotImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        initImageLoader();
        // Bottom layout
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);
        BottomNavigation.bottomNavProvider(bottomNavigationView,getApplicationContext());

        topbarImage  = findViewById(R.id.saveChanges);
        UniversalImageLoader.setImage("", topbarImage, null, "");

//        /**
//         * setup top bar image
//         */
//        topbarImage  = findViewById(R.id.saveChanges);
//        UniversalImageLoader.setImage("", topbarImage, null, "");
        //side button handling...
        sidePollImage = findViewById(R.id.side_poll_image);
        sideQuestionsImage = findViewById(R.id.side_questionIdHome);
        sideChatBotImage = findViewById(R.id.side_questionIdHome);
        sideQuestionsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdminHomeViewFragment adminHomeViewFragment = new AdminHomeViewFragment();
                loadFragment(adminHomeViewFragment);
            }
        });
        topbarImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // Log.d(TAG, "onClick: image clicked");
                TopPopMenu.showPopMenu(v, AdminHome.this);
            }
        });

    }
    private void initImageLoader(){
        UniversalImageLoader universalImageLoader = new UniversalImageLoader(this);
        ImageLoader.getInstance().init(universalImageLoader.getConfig());
    }

    public void loadFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.adminHomeContainerFragment, fragment)
                .addToBackStack(null)
                .commit();
    }
}