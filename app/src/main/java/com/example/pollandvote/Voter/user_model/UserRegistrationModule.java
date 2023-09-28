package com.example.pollandvote.Voter.user_model;

public class UserRegistrationModule {
    private String fullName;
    private String dob;
    private String email;
    private String password;

    private float rating = 0.0f;
    private int feedbackDialogCount = 0;
    private String feedbackDescription;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getFeedbackDialogCount() {
        return feedbackDialogCount;
    }

    public void setFeedbackDialogCount(int feedbackDialogCount) {
        this.feedbackDialogCount = feedbackDialogCount;
    }

    public String getFeedbackDescription() {
        return feedbackDescription;
    }

    public void setFeedbackDescription(String feedbackDescription) {
        this.feedbackDescription = feedbackDescription;
    }
}
