package com.example.pollandvote.Admin.questionnaire;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pollandvote.R;

import java.util.List;
import java.util.Map;

public class QuestionnaireDataAdapter extends RecyclerView.Adapter<QuestionnaireDataAdapter.PollViewHolder> {
    private List<QuestionnaireData> questionnaireDataList;

    public QuestionnaireDataAdapter(List<QuestionnaireData> questionnaireDataList) {
        this.questionnaireDataList = questionnaireDataList;
    }

    @NonNull
    @Override
    public PollViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_item_poll, parent, false);
        return new PollViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PollViewHolder holder, int position) {
        QuestionnaireData questionnaireData = questionnaireDataList.get(position);
        holder.questionTextView.setText(questionnaireData.getQuestion());

        // Display selection type
        holder.selectionTypeTextView.setText("Selection Type: " + questionnaireData.getSelectionType());

        // Display poll options
        Map<String, String> options = questionnaireData.getOptions();
        StringBuilder optionsText = new StringBuilder();
        for (Map.Entry<String, String> entry : options.entrySet()) {
            optionsText.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        holder.optionsTextView.setText(optionsText.toString());

        // Display correct answers
        List<String> correctAnswersList = questionnaireData.getCorrectAnswers();
        StringBuilder correctAnswersText = new StringBuilder("Correct Answers:\n");
        for (String answer : correctAnswersList) {
            correctAnswersText.append(answer).append("\n");
        }
        holder.correctAnswerTextView.setText(correctAnswersText.toString());
    }

    @Override
    public int getItemCount() {
        return questionnaireDataList.size();
    }

    static class PollViewHolder extends RecyclerView.ViewHolder {
        TextView questionTextView;
        TextView selectionTypeTextView;
        TextView optionsTextView;
        TextView correctAnswerTextView;

        PollViewHolder(@NonNull View itemView) {
            super(itemView);
            questionTextView = itemView.findViewById(R.id.questionTextView);
            selectionTypeTextView = itemView.findViewById(R.id.selectionTypeTextView);
            optionsTextView = itemView.findViewById(R.id.optionsTextView);
            correctAnswerTextView = itemView.findViewById(R.id.correctAnswerTextView);
        }
    }
}
