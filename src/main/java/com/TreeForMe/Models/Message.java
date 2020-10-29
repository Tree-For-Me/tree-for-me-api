package com.TreeForMe.Models;


public class Message {

    private String messageContent;
    private int user;

    public Message(String messageContent, int user) {
        this.messageContent = messageContent;
        this.user = user;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }
}
