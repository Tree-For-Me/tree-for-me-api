package com.TreeForMe.Models;

import org.springframework.web.bind.annotation.GetMapping;

public class Message {

    private String messageContent;
    private String user;

    public Message(String messageContent, String user) {
        this.messageContent = messageContent;
        this.user = user;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
