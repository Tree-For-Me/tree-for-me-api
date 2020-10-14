package com.TreeForMe.Models;

import java.util.ArrayList;
import java.util.List;
import com.TreeForMe.Models.AssistantResponse.Intent;

public class Conversation {

    private PlantInfo plantInfo;
    private boolean providedLight;
    private boolean providedHumidity;
    private boolean providedFlowers;

    public Conversation() {
        this.plantInfo = new PlantInfo();
        this.providedLight = false;
        this.providedHumidity = false;
        this.providedFlowers = false;
    }

    public String handleResponse(AssistantResponse ar) {
        for(Intent intent : ar.getIntents()) {
            String name = intent.getName();
            double confidence = intent.getConfidence();
        }

        // TODO: call discovery
        // TODO: populate plantInfo and boolean fields
        // TODO: generate response

        return "This is the response to the user";
    }

}
