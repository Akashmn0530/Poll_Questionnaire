package com.example.pollandvote.Voter.user_questionnaire;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pollandvote.Admin.Utils.UniversalImageLoader;
import com.example.pollandvote.Admin.questionnaire.QuestionnaireData;
import com.example.pollandvote.R;
import com.example.pollandvote.Voter.VoterHome;
import com.example.pollandvote.Voter.bottom_nav.UserBottomNavigation;
import com.example.pollandvote.Voter.bottom_nav.UserTopPopMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserQuestionerActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    ImageView topBarImage;
    private RecyclerView userPollRecyclerView;
    private Button submitAnswersButton;
    private List<QuestionnaireData> questionnaireDataList;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire_poll);
        initImageLoader();

        // Bottom layout
        bottomNavigationView = findViewById(R.id.bottomNavigationViewUser);
        bottomNavigationView.setSelectedItemId(R.id.navigation_dashboard_user);
        UserBottomNavigation.bottomNavProvider(
                bottomNavigationView,
                getApplicationContext(),
                getWindow().getDecorView().getRootView());

        topBarImage = findViewById(R.id.saveChanges);
        UniversalImageLoader.setImage("", topBarImage, null, "");

        progressBar = findViewById(R.id.progress_bar_qn);

        userPollRecyclerView = findViewById(R.id.userPollRecyclerView);
        submitAnswersButton = findViewById(R.id.submitAnswersButton);

        questionnaireDataList = new ArrayList<>();
        UserQuestionnaireAdapter adapter = new UserQuestionnaireAdapter(questionnaireDataList);
        userPollRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        userPollRecyclerView.setAdapter(adapter);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("QuestionnaireData");
        reference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                questionnaireDataList.clear();
                for (DataSnapshot pollSnapshot : dataSnapshot.getChildren()) {
                    QuestionnaireData questionnaireData = pollSnapshot.getValue(QuestionnaireData.class);
                    questionnaireDataList.add(questionnaireData);
                    progressBar.setVisibility(View.GONE);
                }
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
            }
        });

        submitAnswersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswers();
            }
        });
        topBarImage.setOnClickListener(v -> {
            UserTopPopMenu.showPopMenu(v, UserQuestionerActivity.this);
        });
    }

    private void checkAnswers() {
        int correctAnswers = 0;

        for (int i = 0; i < questionnaireDataList.size(); i++) {
            QuestionnaireData questionnaireData = questionnaireDataList.get(i);
            LinearLayout optionsContainer = Objects.requireNonNull(userPollRecyclerView.findViewHolderForAdapterPosition(i))
                    .itemView.findViewById(R.id.optionsContainer);

            if (questionnaireData.getSelectionType().equals("radio")) {
                RadioGroup radioGroup = (RadioGroup) optionsContainer.getChildAt(0);
                int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                RadioButton selectedRadioButton = radioGroup.findViewById(selectedRadioButtonId);

                if (selectedRadioButton != null && selectedRadioButton.getText().toString()
                        .equals(questionnaireData.getCorrectAnswers().get(0))) {
                    correctAnswers++;
                }
            } else if (questionnaireData.getSelectionType().equals("checkbox")) {
                int selectedCorrectAnswers = 0;
                List<String> correctAnswersList = questionnaireData.getCorrectAnswers();

                for (int j = 0; j < optionsContainer.getChildCount(); j++) {
                    CheckBox checkBox = (CheckBox) optionsContainer.getChildAt(j);
                    String optionText = checkBox.getText().toString();

                    if (correctAnswersList.contains(optionText) && checkBox.isChecked()) {
                        selectedCorrectAnswers++;
                    } else if (!correctAnswersList.contains(optionText) && checkBox.isChecked()) {
                        selectedCorrectAnswers = -1; // Incorrect answer selected
                        break;
                    }
                }

                if (selectedCorrectAnswers == correctAnswersList.size()) {
                    correctAnswers++;
                }
            }
        }

        int totalQuestions = questionnaireDataList.size();
        String resultMessage = "You answered " + correctAnswers + " out of " + totalQuestions + " questions correctly.";
        Toast.makeText(this, resultMessage, Toast.LENGTH_LONG).show();
    }
    private void initImageLoader() {
        UniversalImageLoader universalImageLoader = new UniversalImageLoader(this);
        ImageLoader.getInstance().init(universalImageLoader.getConfig());
    }
}
