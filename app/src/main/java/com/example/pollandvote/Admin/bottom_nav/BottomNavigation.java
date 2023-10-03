package com.example.pollandvote.Admin.bottom_nav;

import android.content.Context;
import android.content.Intent;

import com.example.pollandvote.Admin.Polls.PollActivity;
import com.example.pollandvote.Admin.homescreen.AdminHome;
import com.example.pollandvote.Admin.Profiles.AdminProfile;
import com.example.pollandvote.Admin.Notification.NotificationActivity;
import com.example.pollandvote.Admin.Notification.toSend.SendNotifications;
import com.example.pollandvote.R;
import com.example.pollandvote.Admin.questionnaire.QuestionnaireActivity;
import com.example.pollandvote.Voter.notification.UserNotificationActivity;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomNavigation {
    private static boolean isHandlingNavigation = false;
    public static void bottomNavProvider(BottomNavigationView bottomNavigationView, Context context) {
        bottomNavigationView.setOnItemSelectedListener(
                item -> {
                    if (isHandlingNavigation) {
                        return true; // Prevent further execution while handling navigation
                    }

                    isHandlingNavigation = true;

                    int idd = item.getItemId();
                    if (idd == R.id.navigation_home) {
                        // Home logic
                        Intent intent = new Intent(context, AdminHome.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    } else if (idd == R.id.navigation_poll) {
                        // Poll logic PollActivity
                        Intent intent=new Intent(context, PollActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    } else if (idd == R.id.navigation_dashboard) {
                        // Dashboard logic
                        Intent intent=new Intent(context, QuestionnaireActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    } else if (idd == R.id.navigation_notifications) {
                        Intent intent = new Intent(context, NotificationActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        // Notification logic
                    } else if (idd == R.id.navigation_profile) {
                        // Profile logic
                        Intent intent = new Intent(context, AdminProfile.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }

                    // Reset the flag after navigation logic is handled
                    isHandlingNavigation = false;
                    return true;
                }
        );
    }
}
