package com.example.pollandvote.Voter.bottom_nav;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.pollandvote.Admin.Notification.toSend.SendNotifications;
import com.example.pollandvote.Admin.Profiles.AdminProfile;
import com.example.pollandvote.Admin.homescreen.AdminHome;
import com.example.pollandvote.Admin.questionnaire.QuestionnaireActivity;
import com.example.pollandvote.R;
import com.example.pollandvote.Voter.notification.UserNotificationActivity;
import com.example.pollandvote.Voter.user_questionnaire.UserQuestionerActivity;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UserBottomNavigation {
    private static boolean isHandlingNavigation = false;
    static BadgeDrawable badgeDrawable;
    public static void bottomNavProvider(BottomNavigationView bottomNavigationView, Context context) {
        bottomNavigationView.setOnItemSelectedListener(
                item -> {
                    if (isHandlingNavigation) {
                        return true; // Prevent further execution while handling navigation
                    }

                    isHandlingNavigation = true;

                    int idd = item.getItemId();
                    if (idd == R.id.navigation_home_user) {
                        // Home logic
                        Intent intent = new Intent(context, AdminHome.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        Log.d("Akash","top 51");
                    } else if (idd == R.id.navigation_poll_user) {
                        // Poll logic
                        badgeDrawable = bottomNavigationView.getOrCreateBadge(R.id.navigation_notifications);
                        SendNotifications.showNewNotifications(badgeDrawable);
                        //Sending notifications
                        SendNotifications.sendNotificationOperations(context);
                        Log.d("Akash","top 51");
                    } else if (idd == R.id.navigation_dashboard_user) {
                        // Dashboard logic
                        Intent intent=new Intent(context, UserQuestionerActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Log.d("Akash","top 51");
                        context.startActivity(intent);
                    } else if (idd == R.id.navigation_friend) {
//                        Intent intent = new Intent(context, UserNotificationActivity.class);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        context.startActivity(intent);
                        // Notification logic
                    } else if (idd == R.id.navigation_profile_user) {
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
