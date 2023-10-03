package com.example.pollandvote.models;

import java.util.HashMap;
import java.util.Map;

public class PollData {
//    private Map<String, String> question = new HashMap<>();
    private String question;
    private String pollId;
    private boolean checkSelected = false;

    public boolean isCheckSelected() {
        return checkSelected;
    }

    public void setCheckSelected(boolean checkSelected) {
        this.checkSelected = checkSelected;
    }

    private int totalCount = 0;
    private Map<String, Map<String, Object>> options = new HashMap<>();

    public PollData() {
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Map<String, Map<String, Object>> getOptions() {
        return options;
    }

    public void setOptions(Map<String, Map<String, Object>> options) {
        this.options = options;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public String getPollId() {
        return pollId;
    }

    public void setPollId(String pollId) {
        this.pollId = pollId;
    }

    public PollData(String question, String pollId, int totalCount, Map<String, Map<String, Object>> options) {
        this.question = question;
        this.pollId = pollId;
        this.totalCount = totalCount;
        this.options = options;
    }


    public PollData(String question, Map<String, Map<String, Object>> options) {
        this.question = question;
        this.options = options;
    }

    @Override
    public String toString() {
        return "PollData{" +
                "question='" + question + '\'' +
                ", options=" + options +
                '}';
    }
}
