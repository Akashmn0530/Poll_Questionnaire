package com.example.pollandvote.Admin;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pollandvote.Admin.bottom_nav.BottomNavigation;
import com.example.pollandvote.Admin.bottom_nav.TopPopMenu;
import com.example.pollandvote.R;
import com.example.pollandvote.Utils.UniversalImageLoader;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nostra13.universalimageloader.core.ImageLoader;


public class AdminHome extends AppCompatActivity{
    BottomNavigationView bottomNavigationView;
    ImageView topbarImage;
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

        /**
         * setup top bar image
         */
        topbarImage  = findViewById(R.id.saveChanges);
        UniversalImageLoader.setImage("", topbarImage, null, "");

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
}