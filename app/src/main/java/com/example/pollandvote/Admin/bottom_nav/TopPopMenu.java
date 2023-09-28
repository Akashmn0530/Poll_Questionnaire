package com.example.pollandvote.Admin.bottom_nav;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import com.example.pollandvote.Admin.dialogs.AdminFeedBackActivity;
import com.example.pollandvote.Admin.registation.AdminLogin;
import com.example.pollandvote.Admin.registation.RegistrationModule;
import com.example.pollandvote.R;
import com.example.pollandvote.Admin.dialogs.ConfirmLogoutDialog;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class TopPopMenu {

    public static void showPopMenu(View view, Context context){

        PopupMenu popupMenu = new PopupMenu(context, view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.topbar_popup_menu, popupMenu.getMenu());

        Log.d("Akash","28 TopPopMenu");
        // Set a listener for menu item clicks
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.popupLogout){
                    final String ID = AdminLogin.loginId;
                    try {
                        fetchTheData(ID, context);
                    }catch (Exception e) {
                        Log.d("Akash","39");
                        ConfirmLogoutDialog.showLogoutConfirmationDialog(context);
                    }
                    return true;
                }

                return false;
            }
        });

        popupMenu.show();
    }
    static void fetchTheData(String id,Context context){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("AdminRegister").document(id);
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    RegistrationModule c = document.toObject(RegistrationModule.class);
                    assert c != null;
                    if(c.getFeedbackDialogCount() == 0){
                        Intent intent = new Intent(context, AdminFeedBackActivity.class);
                        context.startActivity(intent);
                    }else {
                        ConfirmLogoutDialog.showLogoutConfirmationDialog(context);
                    }
                }else {
                    ConfirmLogoutDialog.showLogoutConfirmationDialog(context);
                }
            }else {
                ConfirmLogoutDialog.showLogoutConfirmationDialog(context);
            }
        });
    }
}
