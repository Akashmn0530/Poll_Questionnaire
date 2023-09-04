package com.example.pollandvote.Admin.Profiles;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.pollandvote.Admin.bottom_nav.BottomNavigation;
import com.example.pollandvote.Admin.bottom_nav.TopPopMenu;
import com.example.pollandvote.R;
import com.example.pollandvote.Admin.Utils.UniversalImageLoader;
import com.example.pollandvote.Admin.dialogs.ConfirmLogoutDialog;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdminProfile extends AppCompatActivity {

    private static final String TAG = "AdminProfile";
    BottomNavigationView bottomNavigationView;
    ImageView profile_image, topbarImage;

    LinearLayout linLay_account, linLay_preferences, linLay_help, linLay_password, linLay_logout;
    public static RelativeLayout adminLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile);

        profile_image = findViewById(R.id.profile_image);
        linLay_account = findViewById(R.id.linLay_account);
        linLay_preferences = findViewById(R.id.linLay_preferences);
        linLay_help = findViewById(R.id.linLay_help);
        linLay_password = findViewById(R.id.linLay_password);
        linLay_logout = findViewById(R.id.linLay_logout);


        /**
         * setup top bar image
         */
        topbarImage  = findViewById(R.id.saveChanges);
        UniversalImageLoader.setImage("", topbarImage, null, "");

        topbarImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "onClick: image clicked");
                TopPopMenu.showPopMenu(v, AdminProfile.this);

            }
        });

        adminLayout = findViewById(R.id.relativeLayout2);

//        Bottom Navigation

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.navigation_profile);
        BottomNavigation.bottomNavProvider(bottomNavigationView,getApplicationContext());


        /**
         * setup image
         */
        UniversalImageLoader.setImage(".getProfile_photo()", profile_image, null, "");
        //Account related...
        linLay_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adminLayout.setVisibility(View.GONE);
                AccountFragment accountFragment = new AccountFragment();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container_profile_data, accountFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        //for preferences fragment
        linLay_preferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adminLayout.setVisibility(View.GONE);
                PreferencesFragment preferencesFragment = new PreferencesFragment();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container_profile_data, preferencesFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });


        /**
         * for help fragment
         */
        linLay_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelpFragment helpFragment = new HelpFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container_profile_data,helpFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });


        /**
         * for Passwords fragment
         */
        linLay_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PasswordChangeFragment passwordChangeFragment = new PasswordChangeFragment();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container_profile_data, passwordChangeFragment)
                        .addToBackStack(null)
                        .commit();

            }
        });


        /**
         * for logout fragment
         */
        linLay_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmLogoutDialog.showLogoutConfirmationDialog(AdminProfile.this);
            }
        });
    }
    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount() > 0){
            getSupportFragmentManager().popBackStack();
        }
        else{
            super.onBackPressed();
            adminLayout.setVisibility(View.VISIBLE);
            getSupportFragmentManager().popBackStack();
        }
    }
}