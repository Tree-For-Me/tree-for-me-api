package com.TreeForMe.Models;

import java.util.ArrayList;
import java.util.List;

public class AssistantResponse {

    public static class Intent {
        private String name;
        private double confidence;
        public Intent(String name, double confidence) {
            this.name = name;
            this.confidence = confidence;
        }

        public String getName() {
            return this.name;
        }

        public double getConfidence() {
            return this.confidence;
        }

    }

    private List<Intent> intents;

    public AssistantResponse() {
        this.intents = new ArrayList<>();
    }

    public List<Intent> getIntents() {
        return this.intents;
    }

    public void addIntent(String name, double confidence) {
        this.intents.add(new Intent(name, confidence));
    }
}
