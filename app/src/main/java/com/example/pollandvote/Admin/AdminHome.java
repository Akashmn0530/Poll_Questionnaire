package com.example.pollandvote.Admin;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pollandvote.Admin.bottom_nav.BottomNavigation;
import com.example.pollandvote.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class AdminHome extends AppCompatActivity{
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        // Bottom layout
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);
        BottomNavigation.bottomNavProvider(bottomNavigationView,getApplicationContext());
    }
}