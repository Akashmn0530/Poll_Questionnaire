package com.example.pollandvote.Admin.dialogs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.pollandvote.Admin.registation.AdminLogin;
import com.example.pollandvote.R;
import com.example.pollandvote.main.MainActivity;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AdminFeedBackActivity extends AppCompatActivity {
    RatingBar ratingBar;
    Button getRating;
    EditText description;
    FirebaseFirestore db;

    ViewFeedback viewFeedback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        getRating = findViewById(R.id.getRating);
        ratingBar = findViewById(R.id.rating);
        description = findViewById(R.id.editTextTextMultiLine);

        // Initialize ViewFeedback object
        viewFeedback = new ViewFeedback();
        // Initialize Firestore
        db = FirebaseFirestore.getInstance();
        getRating.setOnClickListener(v -> {
            float rating = ratingBar.getRating();
            String des = description.getText().toString();
            if (TextUtils.isEmpty(des)) {
                description.setError("Enter Description!");
            } else {
                updateUserStar(rating, des);
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }
    private void updateUserStar(float rating, String des) {
        String id = AdminLogin.loginId;
        if (id != null) {
            DocumentReference update1 = db.collection("AdminRegister").document(id);
            // Update DB
            update1.update("rating", rating, "feedbackDescription", des,"feedbackDialogCount", 1)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(AdminFeedBackActivity.this, "Successfully updated", Toast.LENGTH_SHORT).show();
                        // Add data to FeedbackDB collection
                        addDataToFirestore(rating, des, id);
                    })
                    .addOnFailureListener(e -> Toast.makeText(AdminFeedBackActivity.this, "Failed", Toast.LENGTH_SHORT).show());
        }
    }

    // Add data to the FeedbackDB collection
    private void addDataToFirestore(float ratings, String des, String uid) {
        viewFeedback.setViewRating(ratings);
        viewFeedback.setViewDescription(des);

        db.collection("FeedbackDB").document(uid)
                .set(viewFeedback)
                .addOnSuccessListener(unused -> Toast.makeText(AdminFeedBackActivity.this, "Feedback submitted.", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(AdminFeedBackActivity.this, "Failed to submit feedback.", Toast.LENGTH_SHORT).show());
    }

    public void onTabClickCancel(View view) {
//        @Override
//        public void onBackPressed() {
//            super.onBackPressed();
//        }
    }

    public void onTabClickLogout(View view) {
        ConfirmLogoutDialog.showLogoutConfirmationDialog(AdminFeedBackActivity.this);
    }
}