package com.example.pollandvote.Admin.dialogs;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.pollandvote.Admin.registation.AdminLogin;
import com.example.pollandvote.Admin.registation.RegistrationModule;
import com.example.pollandvote.R;
import com.example.pollandvote.main.MainActivity;

public class ConfirmLogoutDialog {
    public static void showLogoutConfirmationDialog(Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getString(R.string.logout))
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton(context.getString(R.string.logout), (dialog, which) -> {
                    Toast.makeText(context, "Logout", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                })
                .setNegativeButton(context.getString(R.string.cancel), null)
                .show();
    }
}
