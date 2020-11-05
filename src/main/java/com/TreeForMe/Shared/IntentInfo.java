package com.TreeForMe.Shared;

import java.util.*;

public final class IntentInfo {

    public static class IntentGroup {
        public boolean provided;
        public String prompt;
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

    public static Set<String> lightIntents;
    public static Set<String> humidityIntents;
    public static Set<String> flowerIntents;

    public static Map<String, IntentGroup> intentGroups;

    public static Map<String, String> intentSearchMap;
    public static Map<String, List<String>> intentResponseMap;

    // Initialize static variables
    static {
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


        intentGroups = new HashMap<String, IntentGroup>();
        intentGroups.put("light", new IntentGroup(lightIntents, "What kind of light will your plants get?"));
        intentGroups.put("flower", new IntentGroup(flowerIntents, "Do you want flowers on your plant? Or would you prefer just foliage?"));
        intentGroups.put("humidity", new IntentGroup(humidityIntents, "Describe the humidity in your area."));

        intentSearchMap = new HashMap<String, String>();
        intentSearchMap.put("low_humidity", "low");
        intentSearchMap.put("medium_humidity", "medium");
        intentSearchMap.put("high_humidity", "high");
        intentSearchMap.put("low_light", "low light");
        intentSearchMap.put("bright_light", "indirect");
        intentSearchMap.put("direct_light", "direct");
        intentSearchMap.put("has_flowers", "flowering");
        intentSearchMap.put("not_have_flowers", "foliage");

        // TODO: add more responses
        intentResponseMap = new HashMap<String, List<String>>();
        List<String> low_humidity_responses = new ArrayList<String>();
        low_humidity_responses.add("Sounds like you have some dry air!");
        intentResponseMap.put("low_humidity", low_humidity_responses);

        List<String> medium_humidity_responses = new ArrayList<String>();
        medium_humidity_responses.add("Ah, standard air.");
        intentResponseMap.put("medium_humidity", medium_humidity_responses);

        List<String> high_humidity_responses = new ArrayList<String>();
        high_humidity_responses.add("Your air is wet.");
        intentResponseMap.put("high_humidity", high_humidity_responses);

        List<String> low_light_responses = new ArrayList<String>();
        low_light_responses.add("I get it. You want a sneaky spy plant that lives in the shadows.");
        intentResponseMap.put("low_light", low_light_responses);

        List<String> bright_light_responses = new ArrayList<String>();
        bright_light_responses.add("Light that's bright as your future!");
        intentResponseMap.put("bright_light", bright_light_responses);

        List<String> direct_light_responses = new ArrayList<String>();
        direct_light_responses.add("A straight path to the sun.");
        intentResponseMap.put("direct_light", direct_light_responses);

        List<String> has_flowers_responses = new ArrayList<String>();
        has_flowers_responses.add("So, you want some colorful flowers.");
        intentResponseMap.put("has_flowers", has_flowers_responses);

        List<String> not_have_flowers_responses = new ArrayList<String>();
        not_have_flowers_responses.add("You're more of a leaf person. I get it.");
        intentResponseMap.put("not_have_flowers", not_have_flowers_responses);

        List<String> end_conversation_responses = new ArrayList<String>();
        end_conversation_responses.add("Alright, we're done here.");
        intentResponseMap.put("end_conversation", end_conversation_responses);
    }
}
