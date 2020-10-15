package com.TreeForMe.Models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.TreeForMe.Models.AssistantResponse.Intent;

public class Conversation {

    private PlantInfo plantInfo;
    private boolean providedLight;
    private boolean providedHumidity;
    private boolean providedFlowers;

    private boolean finished;

    private double lowestConfidence = .4;

    ArrayList<HashSet<String>> groups = new ArrayList<HashSet<String>>();
    Set<String> lightIntents;
    Set<String> humidityIntents;
    Set<String> flowerIntents;

    public Conversation() {
        this.plantInfo = new PlantInfo();
        this.providedLight = false;
        this.providedHumidity = false;
        this.providedFlowers = false;
        this.lightIntents = new HashSet<String>();
        this.lightIntents.add("bright_light");
        this.lightIntents.add("direct_light");
        this.lightIntents.add("low_light");

        this.flowerIntents = new HashSet<String>();
        this.flowerIntents.add("has_flowers");
        this.flowerIntents.add("not_have_flowers");


        this.humidityIntents = new HashSet<String>();
        this.humidityIntents.add("low_humidity");
        this.humidityIntents.add("medium_humidity");
        this.humidityIntents.add("high_humidity");


        //this.groups.add(this.lightIntents);
    }

    public String handleResponse(AssistantResponse ar) {
        String response = "";


        // Find the max intent for each grouping
        double maxLightIntent = 0;
        String maxLightIntentName = "";
        for(Intent intent : ar.getIntents()) {
            String name = intent.getName();
            double confidence = intent.getConfidence();

            if (lightIntents.contains(name)) {
                if (confidence > maxLightIntent) {
                    maxLightIntent = confidence;
                    maxLightIntentName = name;
                }
            }
            // repeat with other intent types
        }

        // TODO: call discovery


        // TODO: populate plantInfo and boolean fields
        // use the highest light intent if they gave one
        if (maxLightIntent >= lowestConfidence) {
            plantInfo.setLight(maxLightIntentName);
            providedLight = true;
            switch (maxLightIntentName) {
                case "low_light":
                    response = "I get it. You want a sneaky spy plant that lives in the shadows.";
                    break;
                // TODO

                default:
                    // something went wrong
                    response = "I didn't get that";
            }
        }
        // repeat for other types


        // no intents - respond with "I don't understand."?

        // "Tell me something about the plant you're looking for or the environment it will be in. For now, you can talk about humidity or sunlight!"


        // TODO: generate response
        if (!finished) {
            if (!providedLight) {
                // have some sort of selection array for strings?
                return "What kind of light will your plants get?";
            } else if (!providedHumidity) {
                return "Describe the humidity in your area";
            } else if (!providedFlowers) {
                return "Do you want flowers on your plant?";
            } else {
                finished = true; // ?
            }

        } else {
            return "Let's find you the perfect plant...";
        }

        return response;
    }

}
