package com.example.pollandvote.Admin.Profiles;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.example.pollandvote.Admin.Notification.toSend.SendNotifications;
import com.example.pollandvote.R;

public class PreferencesFragment extends Fragment {

    private static final String CHANNEL_ID = "10001"; // Notification channel ID
    private NotificationManager notificationManager;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch notificationEnableSwitch, appModeThemeSwitch, defaultSwitch;
    RelativeLayout appModeIDOptions;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_preferences, container, false);

        appModeIDOptions = view.findViewById(R.id.appModeIDOptions1);

        notificationManager = (NotificationManager) requireContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationEnableSwitch = view.findViewById(R.id.notification_enable_switch);
        updateNotificationSwitchState();
        notificationEnableSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                enableNotifications();
            } else {
                disableNotifications();
            }
        });

        appModeThemeSwitch = view.findViewById(R.id.mode_enable_switch);
        updateModeSwitch();
        appModeThemeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                enableDarkMode();
            } else {
                disableDarkMode();
            }
        });

        defaultSwitch = view.findViewById(R.id.mode_default_switch);
        defaultSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                choiceDefault();
                appModeIDOptions.setVisibility(View.GONE);
            } else {
                appModeIDOptions.setVisibility(View.VISIBLE);
            }
        });
        return view;
    }

    //App mode related...
    public void enableDarkMode(){ AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);}
    public void disableDarkMode(){ AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);}
    public void choiceDefault(){ AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);}

    public void updateModeSwitch(){
        int currentNightMode = AppCompatDelegate.getDefaultNightMode();
        if (currentNightMode == AppCompatDelegate.MODE_NIGHT_YES) {
            appModeThemeSwitch.setChecked(true);
        } else if (currentNightMode == AppCompatDelegate.MODE_NIGHT_NO ||
                currentNightMode == AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM) {
            appModeThemeSwitch.setChecked(false);
        }
    }

    //Notification related...
    private void updateNotificationSwitchState() {
        NotificationChannel channel = notificationManager.getNotificationChannel(CHANNEL_ID);
        if (channel != null) {
            int importance = channel.getImportance();
            boolean notificationsEnabled = importance != NotificationManager.IMPORTANCE_NONE;
            notificationEnableSwitch.setChecked(notificationsEnabled);
        }
    }
    private void enableNotifications() {
            SendNotifications.handleNotifications(requireContext());
    }
    private void disableNotifications() {
            SendNotifications.handleNotifications(requireContext());
    }
}
