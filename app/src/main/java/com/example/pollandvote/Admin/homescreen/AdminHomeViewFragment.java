package com.example.pollandvote.Admin.homescreen;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pollandvote.Admin.questionnaire.QuestionnaireData;
import com.example.pollandvote.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

public class AdminHomeViewFragment extends Fragment {
    private RecyclerView recyclerView;
    public AdminHomeViewFragment() { }
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_home_view, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
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
                AdminQuestionDataAdapter adapter = new AdminQuestionDataAdapter(pollDataList);
                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }
}