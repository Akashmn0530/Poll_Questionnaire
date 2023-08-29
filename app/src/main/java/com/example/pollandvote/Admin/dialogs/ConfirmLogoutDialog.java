package com.example.pollandvote.Admin.dialogs;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.pollandvote.R;

public class ConfirmLogoutDialog {

    public static void showLogoutConfirmationDialog(Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getString(R.string.logout))
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton(context.getString(R.string.logout), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "Logout", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(context.getString(R.string.cancel), null)
                .show();
    }
}
