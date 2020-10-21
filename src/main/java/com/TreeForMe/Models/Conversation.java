package com.TreeForMe.Models;

import java.util.*;

import com.TreeForMe.Models.AssistantResponse.Intent;

public class Conversation {

    private class IntentGroup {
        public boolean provided;
        String prompt;
        public Set<String> intentNames;
        public double maxIntentConfidence;
        public String maxIntentName;

        IntentGroup(Set<String> intentNames, String prompt) {
            this.intentNames = intentNames;
            this.prompt = prompt;
            provided = false;
            this.maxIntentConfidence = 0.0;
            this.maxIntentName = "";
        }
    }

    private PlantInfo plantInfo;

    private boolean askingSpecifics;
    private boolean endConvo;
    public boolean finished;

    private static double LOWEST_CONFIDENCE = .4;

    Set<String> lightIntents;
    Set<String> humidityIntents;
    Set<String> flowerIntents;

    Map<String, IntentGroup> intentGroups;

    Map<String, String> intentSearchMap;
    Map<String, String> intentResponseMap;


    public Conversation() {
        this.plantInfo = new PlantInfo();
        this.endConvo = false;
        this.finished = false;
        this.askingSpecifics = false;

        lightIntents = new HashSet<String>();
        lightIntents.add("bright_light");
        lightIntents.add("direct_light");
        lightIntents.add("low_light");

        flowerIntents = new HashSet<String>();
        flowerIntents.add("has_flowers");
        flowerIntents.add("not_have_flowers");


        humidityIntents = new HashSet<String>();
        humidityIntents.add("low_humidity");
        humidityIntents.add("medium_humidity");
        humidityIntents.add("high_humidity");


        this.intentGroups = new HashMap<String, IntentGroup>();
        this.intentGroups.put("light", new IntentGroup(lightIntents, "What kind of light will your plants get?"));
        this.intentGroups.put("flower", new IntentGroup(flowerIntents, "Do you want flowers on your plant? Or would you prefer just foliage?"));
        this.intentGroups.put("humidity", new IntentGroup(humidityIntents, "Describe the humidity in your area."));

        this.intentSearchMap = new HashMap<String, String>();
        this.intentSearchMap.put("low_humidity", "not humid");
        this.intentSearchMap.put("medium_humidity", "kind of humid");
        this.intentSearchMap.put("high_humidity", "humid");
        this.intentSearchMap.put("low_light", "low light");
        this.intentSearchMap.put("bright_light", "indirect");
        this.intentSearchMap.put("direct_light", "direct");
        this.intentSearchMap.put("has_flowers", "flowering");
        this.intentSearchMap.put("not_have_flowers", "foliage");

        this.intentResponseMap = new HashMap<String, String>();
        this.intentResponseMap.put("low_humidity", "Sounds like you have some dry air!");
        this.intentResponseMap.put("medium_humidity", "Ah, standard air.");
        this.intentResponseMap.put("high_humidity", "Your air is wet.");
        this.intentResponseMap.put("low_light", "I get it. You want a sneaky spy plant that lives in the shadows.");
        this.intentResponseMap.put("bright_light", "Light that's bright as your future!");
        this.intentResponseMap.put("direct_light", "A straight path to the sun.");
        this.intentResponseMap.put("has_flowers", "So, you want some colorful flowers.");
        this.intentResponseMap.put("not_have_flowers", "You're more of a leaf person. I get it.");
        this.intentResponseMap.put("end_conversation", "Alright, we're done here.");

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
            for(IntentGroup ig : this.intentGroups.values()) {
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
        if (!this.intentGroups.get("light").maxIntentName.isEmpty()) {
            String lightInfo = this.intentSearchMap.get(this.intentGroups.get("light").maxIntentName);
            this.plantInfo.setLight(lightInfo);
        }

        if (!this.intentGroups.get("flower").maxIntentName.isEmpty()) {
            String flowerInfo = this.intentSearchMap.get(this.intentGroups.get("flower").maxIntentName);
            this.plantInfo.setFlowers(flowerInfo);
        }

        if (!this.intentGroups.get("humidity").maxIntentName.isEmpty()) {
            String humidityInfo = this.intentSearchMap.get(this.intentGroups.get("humidity").maxIntentName);
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
            for (IntentGroup ig : this.intentGroups.values()) {
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
            response = this.intentResponseMap.get(primaryIntentName);
        }

        this.updateFinished();


        if (!this.finished) {
            if (askingSpecifics) {
                for(IntentGroup ig : this.intentGroups.values()) {
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

}