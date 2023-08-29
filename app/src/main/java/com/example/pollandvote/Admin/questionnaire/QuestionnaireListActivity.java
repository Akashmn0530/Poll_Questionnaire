package com.example.pollandvote.Admin.questionnaire;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.pollandvote.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.*;

import org.checkerframework.checker.nullness.qual.NonNull;

public class QuestionnaireListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire_list);

        recyclerView = findViewById(R.id.recyclerView);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("PollData");

        // Fetch data from Firebase and display in RecyclerView
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<QuestionnaireData> pollDataList = new ArrayList<>();
                for (DataSnapshot pollSnapshot : dataSnapshot.getChildren()) {
                    QuestionnaireData pollData = pollSnapshot.getValue(QuestionnaireData.class);
                    pollDataList.add(pollData);
                }

                QuestionnaireDataAdapter adapter = new QuestionnaireDataAdapter(pollDataList);
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

