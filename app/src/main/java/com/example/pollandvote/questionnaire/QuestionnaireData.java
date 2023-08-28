package com.example.pollandvote.questionnaire;

import java.util.HashMap;
import java.util.Map;

public class QuestionnaireData {
    private String question;
    private Map<String, String> options = new HashMap<>();
    private String correctAnswer;

    public QuestionnaireData() {
        // Default constructor required for Firebase
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

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}