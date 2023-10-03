package com.example.pollandvote.Voter.bottom_nav;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.example.pollandvote.Admin.Notification.toSend.SendNotifications;
import com.example.pollandvote.Admin.Profiles.AdminProfile;
import com.example.pollandvote.Admin.homescreen.AdminHome;
import com.example.pollandvote.Admin.questionnaire.QuestionnaireActivity;
import com.example.pollandvote.R;
import com.example.pollandvote.Voter.VoterHome;
import com.example.pollandvote.Voter.notification.UserNotificationActivity;
import com.example.pollandvote.Voter.user_poll.UserPollActivity;
import com.example.pollandvote.Voter.user_questionnaire.UserQuestionerActivity;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UserBottomNavigation{
    private static boolean isHandlingNavigation = false;
    public static void bottomNavProvider(BottomNavigationView bottomNavigationView, Context context, View view) {
        bottomNavigationView.setOnItemSelectedListener(
                item -> {
                    if (isHandlingNavigation) { return true; }
                    isHandlingNavigation = true;

                    int idd = item.getItemId();
                    if (idd == R.id.navigation_home_user) {
                        // Home logic
                        Intent intent = new Intent(context, VoterHome.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                    else if (idd == R.id.navigation_poll_user) {
                        // Poll logic
                        Intent intent=new Intent(context, UserPollActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                    else if (idd == R.id.navigation_dashboard_user) {
                        // Dashboard logic
                        Intent intent=new Intent(context, UserQuestionerActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                    else if (idd == R.id.navigation_friend) {
                         //Follow logic...
                        //notification button
                        ImageView imageView = view.findViewById(R.id.userNotifications);
                        Drawable vectorDrawable = ContextCompat.getDrawable(context, R.drawable.baseline_favorite_24);
                        if (vectorDrawable != null) {
                            DrawableCompat.setTint(vectorDrawable, Color.RED);
                            imageView.setImageDrawable(vectorDrawable);
                        }
                        //Sending notifications
                        SendNotifications.sendNotificationOperations(context,"Akash","New Friend request arrived...");
                    }
                    else if (idd == R.id.navigation_profile_user) {
                        // Profile logic
                        Intent intent = new Intent(context, AdminProfile.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                    isHandlingNavigation = false;
                    return true;
                }
        );
    }
}
