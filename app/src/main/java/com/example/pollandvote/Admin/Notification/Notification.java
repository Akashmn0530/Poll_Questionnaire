package com.example.pollandvote.Admin.Notification;

public class Notification {
    public String title;
    public String message;
    public String timestamp;

    public Notification(String title, String message, String timestamp) {
        this.title = title;
        this.message = message;
        this.timestamp = timestamp;
    }
}
