package com.example.pollandvote.Voter.user_questionnaire;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pollandvote.Admin.questionnaire.QuestionnaireData;
import com.example.pollandvote.R;

import java.util.List;
import java.util.Map;

public class UserQuestionnaireAdapter extends RecyclerView.Adapter<UserQuestionnaireAdapter.UserPollViewHolder> {

    private List<QuestionnaireData> questionnaireDataList;

    public UserQuestionnaireAdapter(List<QuestionnaireData> questionDataList) {
        this.questionnaireDataList = questionDataList;
    }

    @NonNull
    @Override
    public UserPollViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_user_questionnaire_question, parent, false);
        return new UserPollViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserPollViewHolder holder, int position) {
        QuestionnaireData questionnaireData = questionnaireDataList.get(position);
        holder.questionTextView.setText(questionnaireData.getQuestion());

        holder.optionsContainer.removeAllViews(); // Remove previous options

        if (questionnaireData.getSelectionType().equals("radio")) {
            RadioGroup radioGroup = new RadioGroup(holder.itemView.getContext());
            radioGroup.setOrientation(LinearLayout.VERTICAL);

            Map<String, String> options = questionnaireData.getOptions();
            for (Map.Entry<String, String> entry : options.entrySet()) {
                RadioButton radioButton = new RadioButton(holder.itemView.getContext());
                radioButton.setText(entry.getValue());
                radioButton.setId(entry.getKey().hashCode()); // Use a unique ID for each radio button
                radioGroup.addView(radioButton);
            }

            holder.optionsContainer.addView(radioGroup);
        } else if (questionnaireData.getSelectionType().equals("checkbox")) {
            Map<String, String> options = questionnaireData.getOptions();
            for (Map.Entry<String, String> entry : options.entrySet()) {
                CheckBox checkBox = new CheckBox(holder.itemView.getContext());
                checkBox.setText(entry.getValue());
                checkBox.setId(entry.getKey().hashCode()); // Use a unique ID for each checkbox
                holder.optionsContainer.addView(checkBox);
            }
        }
    }

    @Override
    public int getItemCount() {
        return questionnaireDataList.size();
    }

    static class UserPollViewHolder extends RecyclerView.ViewHolder {
        TextView questionTextView;
        LinearLayout optionsContainer;

        UserPollViewHolder(@NonNull View itemView) {
            super(itemView);
            questionTextView = itemView.findViewById(R.id.questionTextView);
            optionsContainer = itemView.findViewById(R.id.optionsContainer);
        }
    }
}
