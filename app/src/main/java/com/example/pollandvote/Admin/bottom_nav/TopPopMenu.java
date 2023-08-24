package com.example.pollandvote.Admin.bottom_nav;

import android.content.Context;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.pollandvote.R;
import com.example.pollandvote.dialogs.ConfirmLogoutDialog;

public class TopPopMenu {

    public static void showPopMenu(View view, Context context){

        PopupMenu popupMenu = new PopupMenu(context, view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.topbar_popup_menu, popupMenu.getMenu());

        // Set a listener for menu item clicks
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.popupLogout){

//                    Toast.makeText(context, "Logout.", Toast.LENGTH_SHORT).show();

                    ConfirmLogoutDialog.showLogoutConfirmationDialog(context);
                    return true;
                }

                return false;
            }
        });

        popupMenu.show();
    }
}
