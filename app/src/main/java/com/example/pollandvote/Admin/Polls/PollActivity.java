package com.example.pollandvote.Admin.Polls;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pollandvote.Admin.Notification.toSend.SendNotifications;
import com.example.pollandvote.Admin.Utils.UniversalImageLoader;
import com.example.pollandvote.Admin.bottom_nav.BottomNavigation;
import com.example.pollandvote.Admin.bottom_nav.TopPopMenu;
import com.example.pollandvote.Admin.questionnaire.QuestionnaireActivity;
import com.example.pollandvote.R;
import com.example.pollandvote.models.PollData;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.HashMap;
import java.util.Map;

public class PollActivity extends AppCompatActivity {
    private ImageView vectorAsset, arrowbtn, vectorremove;
    private Button button;
    private Map<String, String> question = new HashMap<>();
    private EditText editTextpoll, edittextID, editTextCorrectAnswer, editTextQuestionId; // New edit text for correct answer
    BottomNavigationView bottomNavigationView;
    ImageView topBarImage;
    private LinearLayout pollOptionsContainer;
    private int pollOptionCount = 0;
    private Map<String, String> options = new HashMap<>();
    private Map<String,Map<String,Object>> votes = new HashMap<>(); //to count vote with options
    private FirebaseDatabase db;
    private PollData pollData;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poll);

        initImageLoader();
        // Bottom layout
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.navigation_poll);
        BottomNavigation.bottomNavProvider(bottomNavigationView,getApplicationContext());

        topBarImage  = findViewById(R.id.saveChanges);
        UniversalImageLoader.setImage("", topBarImage, null, "");

        db = FirebaseDatabase.getInstance();
        reference = db.getReference(getString(R.string.dbname_adminpolldata));

        editTextQuestionId = findViewById(R.id.questionid);
        editTextpoll = findViewById(R.id.edittxtaddpoll);
        // editTextCorrectAnswer = findViewById(R.id.editTextCorrectAnswer); // Initialize the edit text for correct answer
        arrowbtn = findViewById(R.id.nextarrow);
        vectorAsset = findViewById(R.id.vectorAsset);
        vectorremove = findViewById(R.id.vectorremove);
        pollOptionsContainer = findViewById(R.id.pollOptionsContainer);
        button = findViewById(R.id.viewdata);

        vectorAsset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pollOptionCount < 4) {
                    pollOptionCount++;
                    addPollOption();
                } else {
                    Toast.makeText(PollActivity.this, "Maximum options reached!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        vectorremove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pollOptionCount > 0) {
                    removePollOption();
                } else {
                    Toast.makeText(PollActivity.this, "No options to remove", Toast.LENGTH_SHORT).show();
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PollActivity.this, "Display the data", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(PollActivity.this, PollListActivity.class));
            }
        });

        arrowbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pollQuestion = editTextpoll.getText().toString();
                question.put(getString(R.string.dbname_question), editTextpoll.getText().toString());

                Map<String,Integer> op = new HashMap<>();

                for (int i = 0; i < pollOptionCount; i++) {
                    EditText editText = (EditText) pollOptionsContainer.getChildAt(i);
                    String pollOption = editText.getText().toString();

                    votes.put("option_" + i, new HashMap<String, Object>(){{
                        put(getString(R.string.dbname_polloption), pollOption);
                        put(getString(R.string.dbname_count), 0);
                    }
                    });
                }

                //                votes.put(getString(R.string.dbname_options), op);
                pollData = new PollData(editTextpoll.getText().toString(),editTextQuestionId.getText().toString(),0, votes);

                String pollDataDb = editTextQuestionId.getText().toString();

                reference.child(pollDataDb).setValue(pollData)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(PollActivity.this, "Data added to Realtime Database", Toast.LENGTH_SHORT).show();

                                //Setting badge and Sending notifications
                                bottomNavigationView = findViewById(R.id.bottomNavigationView);
                                BadgeDrawable badgeDrawable = bottomNavigationView.getOrCreateBadge(R.id.navigation_notifications);
                                SendNotifications.showNewNotifications(badgeDrawable);
                                SendNotifications.sendNotificationOperations(PollActivity.this, "Poll",editTextpoll.getText().toString());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(PollActivity.this, "Error adding data to Realtime Database", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        topBarImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Log.d(TAG, "onClick: image clicked");
                TopPopMenu.showPopMenu(v, PollActivity.this);
            }
        });
    }

    private void addPollOption() {
        EditText editText = new EditText(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        editText.setLayoutParams(layoutParams);
        pollOptionsContainer.addView(editText);
    }

    private void removePollOption() {
        pollOptionsContainer.removeViewAt(pollOptionCount - 1);
        pollOptionCount--;
    }
    private void initImageLoader(){
        UniversalImageLoader universalImageLoader = new UniversalImageLoader(this);
        ImageLoader.getInstance().init(universalImageLoader.getConfig());
    }
}
