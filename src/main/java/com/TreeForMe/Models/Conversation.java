package com.TreeForMe.Models;

import java.util.*;

import com.TreeForMe.Models.AssistantResponse.Intent;
import com.TreeForMe.Shared.IntentInfo;

public class Conversation {


    private PlantInfo plantInfo;

    private boolean askingSpecifics;
    private boolean endConvo;
    public boolean finished;

    private static double LOWEST_CONFIDENCE = .4;
    private Random random;

    public Conversation() {
        this.plantInfo = new PlantInfo();
        this.endConvo = false;
        this.finished = false;
        this.askingSpecifics = false;
        this.random = new Random();

    }

    private String processIntents(AssistantResponse ar) {
        String primaryIntentName = "";
        double primaryIntentConfidence = 0.0;

        // Find the max intent for each grouping
        for(Intent intent : ar.getIntents()) {
            String name = intent.getName();
            double confidence = intent.getConfidence();
            if (name.equals("end_conversation") && confidence > LOWEST_CONFIDENCE) {
                this.endConvo = true;
                if (confidence > primaryIntentConfidence) {
                    primaryIntentConfidence = confidence;
                    primaryIntentName = name;
                }
            }
            for(IntentInfo.IntentGroup ig : IntentInfo.intentGroups.values()) {
                if (ig.intentNames.contains(name)) {
                    if (confidence > ig.maxIntentConfidence && confidence > LOWEST_CONFIDENCE) {
                        ig.maxIntentConfidence = confidence;
                        ig.maxIntentName = name;
                        ig.provided = true;
                        if (confidence > primaryIntentConfidence) {
                            primaryIntentConfidence = confidence;
                            primaryIntentName = name;
                        }
                    }
                }
            }
        }

        return primaryIntentName;
    }

    //TODO
    private void fillPlantInfo() {
        // populate plantInfo
        if (!IntentInfo.intentGroups.get("light").maxIntentName.isEmpty()) {
            String lightInfo = IntentInfo.intentSearchMap.get(IntentInfo.intentGroups.get("light").maxIntentName);
            this.plantInfo.setLight(lightInfo);
        }

        if (!IntentInfo.intentGroups.get("flower").maxIntentName.isEmpty()) {
            String flowerInfo = IntentInfo.intentSearchMap.get(IntentInfo.intentGroups.get("flower").maxIntentName);
            this.plantInfo.setFlowers(flowerInfo);
        }

        if (!IntentInfo.intentGroups.get("humidity").maxIntentName.isEmpty()) {
            String humidityInfo = IntentInfo.intentSearchMap.get(IntentInfo.intentGroups.get("humidity").maxIntentName);
            this.plantInfo.setHumidity(humidityInfo);
        }

        /*
        for (Map.Entry<String, IntentGroup> igEntry : this.intentGroups.entrySet()) {
            String intentGroupName = igEntry.getKey();
            IntentGroup ig = igEntry.getValue();

            // if higher than certain value than set the confidence
            if (this.ig.maxIntentConfidence > LOWEST_CONFIDENCE) {
                Class plantInfoClass = plantInfo.getClass();
                Field field = plantInfoClass.getDeclaredField(intentGroupName);
                field.set(ig.maxIntentName)

                // when do we get response?
            }
        }
        */
    }

    private void updateFinished() {
        this.finished = true;
        if (!this.endConvo) {
            for (IntentInfo.IntentGroup ig : IntentInfo.intentGroups.values()) {
                if (!ig.provided) {
                    this.finished = false;
                }
            }
        }
    }

    private String getResponse(String primaryIntentName) {
        String response = "";
        if (primaryIntentName.isEmpty()) {
            response = "I didn't understand.";
            this.askingSpecifics = true;
        }
        else {
            List<String> responses = IntentInfo.intentResponseMap.get(primaryIntentName);
            int randomResponseNum = this.random.nextInt(responses.size());
            response = responses.get(randomResponseNum);
        }

        this.updateFinished();


        if (!this.finished) {
            if (askingSpecifics) {
                for(IntentInfo.IntentGroup ig : IntentInfo.intentGroups.values()) {
                    if (!ig.provided) {
                        response += "\n" + ig.prompt;
                        break;
                    }
                }
            }
            else {
                response += "\nTell me something about the plant you're looking for or the environment it will be in. For now, you can talk about humidity, flowers, or sunlight!";
            }

        } else {
            response += "\nLet's find you the perfect plant...";
        }

        return response;
    }


    public String handleResponse(AssistantResponse ar) {
        // reset from last time we got a response
        for(IntentInfo.IntentGroup ig : IntentInfo.intentGroups.values()) {
            ig.maxIntentConfidence = 0.0;
            ig.maxIntentName = "";
        }

        String primaryIntentName = this.processIntents(ar);

        this.fillPlantInfo();

        String response = this.getResponse(primaryIntentName);

        return response;
    }

    public PlantInfo getPlantInfo() {
        return this.plantInfo;
    }

}