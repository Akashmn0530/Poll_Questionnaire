package com.example.pollandvote.Voter.bottom_nav;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.pollandvote.Admin.dialogs.ConfirmLogoutDialog;
import com.example.pollandvote.Admin.dialogs.AdminFeedBackActivity;
import com.example.pollandvote.Admin.registation.AdminLogin;
import com.example.pollandvote.Admin.registation.RegistrationModule;
import com.example.pollandvote.R;
import com.example.pollandvote.Voter.register_login.LoginUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserTopPopMenu {

    public static void showPopMenu(View view, Context context){

        PopupMenu popupMenu = new PopupMenu(context, view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.topbar_popup_menu, popupMenu.getMenu());
        Log.d("Akash", "onClick: image clicked UserTopPopMenu");
        // Set a listener for menu item clicks
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.popupLogout){
                    final String ID = LoginUser.userLoginID;
                    try {
                        fetchTheData(ID, context);
                    }catch (Exception e) {
                        Log.d("Akash",""+e);
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
        DocumentReference docRef = db.collection("VoterRegister").document(id);
        Log.d("Akash","top 51");
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    RegistrationModule c = document.toObject(RegistrationModule.class);
                    Log.d("Akash","top 57");
                    assert c != null;
                    if(c.getFeedbackDialogCount() == 0){
                        Log.d("Akash","top 60");
                        //SetFeedBackDialogCount to 1
                        DocumentReference update1 = db.collection("VoterRegister").document(id);
                        //Update DB
                        update1
                                .update("feedbackDialogCount", 1)
                                .addOnSuccessListener(aVoid -> Toast.makeText(context, "Successfully updated", Toast.LENGTH_SHORT).show())
                                .addOnFailureListener(e -> Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show());
                        Log.d("Akash","top 76");
                        Intent intent = new Intent(context, AdminFeedBackActivity.class);
                        context.startActivity(intent);
                    }else {
                        Log.d("Akash","top 72");
                        ConfirmLogoutDialog.showLogoutConfirmationDialog(context);
                    }
                }else {
                    Log.d("Akash","top 76");
                    ConfirmLogoutDialog.showLogoutConfirmationDialog(context);
                }
            }else {
                Log.d("Akash","top 80");
                ConfirmLogoutDialog.showLogoutConfirmationDialog(context);
            }
        });
    }
}
