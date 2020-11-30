package com.TreeForMe.Models;

import java.util.*;

import com.TreeForMe.Models.AssistantResponse.Intent;
import com.TreeForMe.Shared.IntentInfo;
import com.TreeForMe.Shared.IntentInfo.IntentGroup;

public class Conversation {


    private PlantInfo plantInfo;

    private boolean askingSpecifics;
    private boolean endConvo;
    private boolean finished;

    private String oldQuery;
    private List<Plant> oldBestPlants;

    private Map<String, IntentGroup> intentGroups;

    /* null if generic, corresponding intent group if specific */
    private IntentGroup previousQuestion;

    private static final double LOWEST_CONFIDENCE = .45;
    private Random random;

    public Conversation() {
        this.plantInfo = new PlantInfo();
        this.endConvo = false;
        this.finished = false;
        this.askingSpecifics = false;
        this.previousQuestion = null;
        this.random = new Random();
        this.intentGroups = IntentInfo.getNewIntentGroups();
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

            if (confidence > LOWEST_CONFIDENCE && confidence > primaryIntentConfidence && (IntentInfo.lightIntents.contains(name) || IntentInfo.humidityIntents.contains(name) || IntentInfo.flowerIntents.contains(name))) {
                primaryIntentConfidence = confidence;
                primaryIntentName = name;
            }

            /* Match intent name */
            if (name.equals("end_conversation") && confidence > LOWEST_CONFIDENCE) {
                this.endConvo = true;
            }
            else if (name.equals("non_answer") && confidence > LOWEST_CONFIDENCE) {
                if (this.previousQuestion != null) {
                    this.previousQuestion.provided = true;
                }
                else {
                    this.askingSpecifics = true;
                }
            }
            else {
                for (IntentGroup ig : this.intentGroups.values()) {
                    if (ig.intentNames.contains(name) && confidence > LOWEST_CONFIDENCE && confidence > ig.maxIntentConfidence) {
                        ig.maxIntentConfidence = confidence;
                        ig.maxIntentName = name;
                        ig.provided = true;
                    }
                }
            }
        }

        if (primaryIntentName.isEmpty()) {
            this.askingSpecifics = true;
        }

        return primaryIntentName;
    }

    private void fillPlantInfo() {
        // populate plantInfo
        if (!this.intentGroups.get("light").maxIntentName.isEmpty()) {
            String lightInfo = IntentInfo.intentSearchMap.get(this.intentGroups.get("light").maxIntentName);
            this.plantInfo.setLight(lightInfo);
        }

        if (!this.intentGroups.get("flower").maxIntentName.isEmpty()) {
            String flowerInfo = IntentInfo.intentSearchMap.get(this.intentGroups.get("flower").maxIntentName);
            this.plantInfo.setFlowers(flowerInfo);
        }

        if (!this.intentGroups.get("humidity").maxIntentName.isEmpty()) {
            String humidityInfo = IntentInfo.intentSearchMap.get(this.intentGroups.get("humidity").maxIntentName);
            this.plantInfo.setHumidity(humidityInfo);
        }

    }

    private void updateFinished() {
        this.finished = true;
        if (!this.endConvo) {
            for (IntentGroup ig : this.intentGroups.values()) {
                if (!ig.provided) {
                    this.finished = false;
                    break;
                }
            }
        }
    }

    private String getRandomResponse(List<String> listOfResponses) {
        int randomResponseNum = this.random.nextInt(listOfResponses.size());
        return listOfResponses.get(randomResponseNum);
    }

    private String getResponse(String primaryIntentName) {
        /* Respond to user's answer */
        String response = "";
        if (primaryIntentName.isEmpty()) {
            response = getRandomResponse(IntentInfo.notUnderstandResponses);
        }
        else {
            // Get response depending on the intent
            List<String> intentResponses = IntentInfo.intentResponseMap.get(primaryIntentName);
            response = getRandomResponse(intentResponses);
        }

        /* Ask next question */
        this.updateFinished();
        if (!this.finished) {
            if (askingSpecifics) {
                for(IntentGroup ig : this.intentGroups.values()) {
                    if (!ig.provided) {
                        response += "\n" + getRandomResponse(ig.prompts);
                        this.previousQuestion = ig;
                        break;
                    }
                }
            }
            else {
                response += getRandomResponse(IntentInfo.genericResponses);
                this.previousQuestion = null;
            }

        } else {
            response += getRandomResponse(IntentInfo.convoOverResponses);
        }

        return response;
    }


    public String handleResponse(AssistantResponse ar) {
        // reset from last time we got a response
        for(IntentGroup ig : this.intentGroups.values()) {
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

    public boolean isFinished() {
        return finished;
    }

    public String getOldQuery() { return oldQuery; }
    public void setOldQuery(String oldQuery) {
        this.oldQuery = oldQuery;
    }
    public List<Plant> getOldBestPlants() { return oldBestPlants; }
    public void setOldBestPlants(List<Plant> oldBestPlants) {
        this.oldBestPlants = oldBestPlants;
    }
}