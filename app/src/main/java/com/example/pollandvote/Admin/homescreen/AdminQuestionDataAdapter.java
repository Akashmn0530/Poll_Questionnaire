package com.example.pollandvote.Admin.homescreen;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pollandvote.Admin.questionnaire.QuestionnaireData;
import com.example.pollandvote.R;

import java.util.List;
import java.util.Map;

public class AdminQuestionDataAdapter extends RecyclerView.Adapter<com.example.pollandvote.Admin.homescreen.AdminQuestionDataAdapter.PollViewHolder> {
    private List<QuestionnaireData> pollDataList;

    public AdminQuestionDataAdapter(List<QuestionnaireData> pollDataList) {
        this.pollDataList = pollDataList;
    }

    @NonNull
    @Override
    public com.example.pollandvote.Admin.homescreen.AdminQuestionDataAdapter.PollViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.admin_questions_data_items, parent, false);
        return new com.example.pollandvote.Admin.homescreen.AdminQuestionDataAdapter.PollViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull com.example.pollandvote.Admin.homescreen.AdminQuestionDataAdapter.PollViewHolder holder, int position) {
        QuestionnaireData pollData = pollDataList.get(position);
        holder.questionTextView.setText(pollData.getQuestion());

        // Display poll options
//        Map<String, String> options = pollData.getOptions();
//        StringBuilder optionsText = new StringBuilder();
//        for (Map.Entry<String, String> entry : options.entrySet()) {
//            optionsText.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
//        }
//        holder.optionsTextView.setText(optionsText.toString());
//
//        // Display correct answer
//        holder.correctAnswerTextView.setText("Correct Answer: " + pollData.getCorrectAnswer());
    }

    @Override
    public int getItemCount() {
        return pollDataList.size();
    }

    static class PollViewHolder extends RecyclerView.ViewHolder {
        TextView questionTextView;
//        TextView optionsTextView;
//        TextView correctAnswerTextView;

        PollViewHolder(@NonNull View itemView) {
            super(itemView);
            questionTextView = itemView.findViewById(R.id.questionTextView);
//            optionsTextView = itemView.findViewById(R.id.optionsTextView);
//            correctAnswerTextView = itemView.findViewById(R.id.correctAnswerTextView);
        }
    }
}
