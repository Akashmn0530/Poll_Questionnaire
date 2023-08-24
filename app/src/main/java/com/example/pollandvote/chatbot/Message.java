package com.example.pollandvote.chatbot;


public class Message {

    private String message;
    private String sender;

    // constructor.
    public Message(String message, String sender) {
        this.message = message;
        this.sender = sender;
    }

    // getter and setter methods.
    public String getMessage() {
        return message;
    }
    public String getSender() {
        return sender;
    }

}

