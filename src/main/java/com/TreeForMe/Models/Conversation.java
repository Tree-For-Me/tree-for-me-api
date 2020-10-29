package com.TreeForMe.Models;

import java.util.*;

import com.TreeForMe.Models.AssistantResponse.Intent;
import com.TreeForMe.Shared.IntentInfo;

public class Conversation {


    private PlantInfo plantInfo;

    private boolean askingSpecifics;
    private boolean endConvo;
    public boolean finished;

    private static final double LOWEST_CONFIDENCE = .3;
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
        System.out.println("\nIntents:");
        for(Intent intent : ar.getIntents()) {
            System.out.println(intent);
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
            response = getRandomResponse(IntentInfo.notUnderstandResponses);
            this.askingSpecifics = true;
        }
        else {
            // Get response depending on the intent
            List<String> intentResponses = IntentInfo.intentResponseMap.get(primaryIntentName);
            response = getRandomResponse(intentResponses);
        }

        this.updateFinished();


        if (!this.finished) {
            if (askingSpecifics) {
                for(IntentInfo.IntentGroup ig : IntentInfo.intentGroups.values()) {
                    if (!ig.provided) {
                        response += "\n" + getRandomResponse(ig.prompts);
                        break;
                    }
                }
            }
            else {
                response += getRandomResponse(IntentInfo.genericResponses);
            }

        } else {
            response += getRandomResponse(IntentInfo.convoOverResponses);
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

    private String getRandomResponse(List<String> listOfResponses) {
        int randomResponseNum = this.random.nextInt(listOfResponses.size());
        return listOfResponses.get(randomResponseNum);
    }
}