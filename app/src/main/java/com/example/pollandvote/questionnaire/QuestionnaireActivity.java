package com.example.pollandvote.questionnaire;

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

import com.example.pollandvote.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class QuestionnaireActivity extends AppCompatActivity {
    private ImageView vectorAsset, arrowbtn, vectorremove;
    private Button button;
    private EditText editTextpoll, edittextID, editTextCorrectAnswer; // New edit text for correct answer

    private LinearLayout pollOptionsContainer;
    private int pollOptionCount = 0;
    private Map<String, String> options = new HashMap<>();
    private FirebaseDatabase db;

    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);

        db = FirebaseDatabase.getInstance();
        reference = db.getReference("PollData");

        editTextpoll = findViewById(R.id.edittxtaddpoll);
        edittextID = findViewById(R.id.questionid);
        editTextCorrectAnswer = findViewById(R.id.editTextCorrectAnswer); // Initialize the edit text for correct answer
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
                    Toast.makeText(QuestionnaireActivity.this, "Maximum options reached!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        vectorremove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pollOptionCount > 0) {
                    removePollOption();
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
                String pollQuestion = editTextpoll.getText().toString();
                options.put("question", pollQuestion);

                QuestionnaireData pollData = new QuestionnaireData(pollQuestion);

                for (int i = 0; i < pollOptionCount; i++) {
                    EditText editText = (EditText) pollOptionsContainer.getChildAt(i);
                    String pollOption = editText.getText().toString();
                    pollData.getOptions().put("Option_" + i, pollOption);
                    options.put("option_" + i, pollOption);
                }

                String correctAnswer = editTextCorrectAnswer.getText().toString(); // Get the correct answer from user input
                pollData.setCorrectAnswer(correctAnswer);

                String userDefinedDocumentName = edittextID.getText().toString();

                reference.child(userDefinedDocumentName).setValue(pollData)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(QuestionnaireActivity.this, "Data added to Realtime Database", Toast.LENGTH_SHORT).show();
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
}