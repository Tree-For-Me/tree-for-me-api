package com.TreeForMe.Models;

import java.util.ArrayList;
import java.util.List;

public class AssistantResponse {

    private List<String> messages;
    private List<String> intents;

    public AssistantResponse() {
        this.messages = new ArrayList<>();
        this.intents = new ArrayList<>();
    }

    public List<String> getMessages() {
        return this.messages;
    }

    public void addMessage(String message) {
        this.messages.add(message);
    }

    public List<String> getIntents() {
        return this.intents;
    }

    public void addIntent(String intent) {
        this.intents.add(intent);
    }
}
