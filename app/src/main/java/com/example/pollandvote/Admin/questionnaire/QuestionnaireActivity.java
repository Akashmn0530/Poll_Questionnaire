package com.example.pollandvote.Admin.questionnaire;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pollandvote.Admin.Notification.toSend.SendNotifications;
import com.example.pollandvote.Admin.Polls.PollActivity;
import com.example.pollandvote.Admin.Utils.UniversalImageLoader;
import com.example.pollandvote.Admin.bottom_nav.BottomNavigation;
import com.example.pollandvote.Admin.bottom_nav.TopPopMenu;
import com.example.pollandvote.Admin.homescreen.AdminHome;
import com.example.pollandvote.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestionnaireActivity extends AppCompatActivity {
    private ImageView vectorAsset, arrowbtn, vectorremove;
    private Button button;
    BottomNavigationView bottomNavigationView;
    ImageView topBarImage;
    Spinner spinnerLanguages;

    private EditText editTextpoll, edittextID, editTextCorrectAnswer,selectionType; // Updated variable name
    private String selectedLanguage;

    private LinearLayout questionOptionsContainer;
    private int QuestionnaireOptionCount = 0;
    private Map<String, String> options = new HashMap<>();
    private FirebaseDatabase db;

    private DatabaseReference reference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);

        initImageLoader();
        // Bottom layout
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.navigation_dashboard);
        BottomNavigation.bottomNavProvider(bottomNavigationView,getApplicationContext());

        topBarImage  = findViewById(R.id.saveChanges);
        UniversalImageLoader.setImage("", topBarImage, null, "");

        db = FirebaseDatabase.getInstance();
        reference = db.getReference(getString(R.string.dbname_questionnairedata));

        editTextpoll = findViewById(R.id.edittxtaddpoll);
        edittextID = findViewById(R.id.questionid);
        editTextCorrectAnswer = findViewById(R.id.editTextCorrectAnswer);// Updated variable name
        //selectionType=findViewById(R.id.SelectRadioOrCheckbox);
        spinnerLanguages = findViewById(R.id.spinner);

        arrowbtn = findViewById(R.id.nextarrow);
        vectorAsset = findViewById(R.id.vectorAsset);
        vectorremove = findViewById(R.id.vectorremove);
        questionOptionsContainer = findViewById(R.id.QuestionnaireOptionsContainer);
        button = findViewById(R.id.viewdata);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.languages, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // Use dropdown item layout

        spinnerLanguages.setAdapter(adapter);

        spinnerLanguages.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Get the selected item from the Spinner and store it in the selectedLanguage variable
                selectedLanguage = (String) parentView.getItemAtPosition(position);

                // Display the selected language in a Toast message
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Handle the case when nothing is selected (if needed)
                selectedLanguage = null; // Set to null or an appropriate default value
            }
        });

        vectorAsset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (QuestionnaireOptionCount < 4) {
                    QuestionnaireOptionCount++;
                    addOptions();
                } else {
                    Toast.makeText(QuestionnaireActivity.this, "Maximum options reached!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        vectorremove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (QuestionnaireOptionCount > 0) {
                    removeOptions();
                } else {
                    Toast.makeText(QuestionnaireActivity.this, "No options to remove", Toast.LENGTH_SHORT).show();
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(QuestionnaireActivity.this, "Display the data", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(QuestionnaireActivity.this, QuestionnaireListActivity.class));
            }
        });

        arrowbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Question = editTextpoll.getText().toString();

                QuestionnaireData questionnaireData = new QuestionnaireData(Question);

                for (int i = 0; i < QuestionnaireOptionCount; i++) {
                    EditText editText = (EditText) questionOptionsContainer.getChildAt(i);
                    String Options = editText.getText().toString();
                    questionnaireData.getOptions().put("Option_" + i, Options);
                }

                // String selectionType1 = selectedLanguage.getText().toString();
                questionnaireData.setSelectionType(selectedLanguage);

                String correctAnswer = editTextCorrectAnswer.getText().toString();
                String[] correctAnswersArray = correctAnswer.split(",");

                List<String> formattedCorrectAnswers = new ArrayList<>();
                for (String answer : correctAnswersArray) {
                    formattedCorrectAnswers.add(answer.trim());
                }

                questionnaireData.setCorrectAnswers(formattedCorrectAnswers);

                String userDefinedDocumentName = edittextID.getText().toString();

                reference.child(userDefinedDocumentName).setValue(questionnaireData)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(QuestionnaireActivity.this, "Data added to Realtime Database", Toast.LENGTH_SHORT).show();
                                //Setting badge and Sending notifications
                                bottomNavigationView = findViewById(R.id.bottomNavigationView);
                                BadgeDrawable badgeDrawable = bottomNavigationView.getOrCreateBadge(R.id.navigation_notifications);
                                SendNotifications.showNewNotifications(badgeDrawable);
                                SendNotifications.sendNotificationOperations(QuestionnaireActivity.this,"Questionnaire",editTextpoll.getText().toString());

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(QuestionnaireActivity.this, "Error adding data to Realtime Database", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        topBarImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Log.d(TAG, "onClick: image clicked");
                TopPopMenu.showPopMenu(v, QuestionnaireActivity.this);
            }
        });
    }

    private void addOptions() {
        EditText editText = new EditText(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        editText.setLayoutParams(layoutParams);
        questionOptionsContainer.addView(editText);
    }

    private void removeOptions() {
        questionOptionsContainer.removeViewAt(QuestionnaireOptionCount - 1);
        QuestionnaireOptionCount--;
    }
    private void initImageLoader(){
        UniversalImageLoader universalImageLoader = new UniversalImageLoader(this);
        ImageLoader.getInstance().init(universalImageLoader.getConfig());
    }
}
