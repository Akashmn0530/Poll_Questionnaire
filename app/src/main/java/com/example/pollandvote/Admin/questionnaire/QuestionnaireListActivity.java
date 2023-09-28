package com.example.pollandvote.Admin.questionnaire;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pollandvote.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class QuestionnaireListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire_list);

        recyclerView = findViewById(R.id.recyclerView);
        reference = FirebaseDatabase.getInstance().getReference("QuestionnaireData");

        // Fetch data from Firebase and display in RecyclerView
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<QuestionnaireData> questionnaireData = new ArrayList<>();
                for (DataSnapshot pollSnapshot : dataSnapshot.getChildren()) {
                    QuestionnaireData data = pollSnapshot.getValue(QuestionnaireData.class);
                    questionnaireData.add(data);
                }

                QuestionnaireDataAdapter adapter = new QuestionnaireDataAdapter(questionnaireData);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
