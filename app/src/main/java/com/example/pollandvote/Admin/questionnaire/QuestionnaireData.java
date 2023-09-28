package com.example.pollandvote.Admin.questionnaire;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestionnaireData {
    private String question;
    private Map<String, String> options = new HashMap<>();
    private List<String> correctAnswers = new ArrayList<>(); // Updated field

    private String selectionType;

    public QuestionnaireData() {
        // Default constructor required for Firebase
    }

    public String getSelectionType() {
        return selectionType;
    }

    public void setSelectionType(String selectionType) {
        this.selectionType = selectionType;
    }

    public QuestionnaireData(String question) {
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Map<String, String> getOptions() {
        return options;
    }

    public void setOptions(Map<String, String> options) {
        this.options = options;
    }

    public List<String> getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(List<String> correctAnswers) {
        this.correctAnswers = correctAnswers;
    }
}
