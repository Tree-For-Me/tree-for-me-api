package com.TreeForMe.Shared;

import java.util.*;

public final class IntentInfo {

    public static class IntentGroup {
        public boolean provided;
        public List<String> prompts;
        public Set<String> intentNames;
        public double maxIntentConfidence;
        public String maxIntentName;

        IntentGroup(Set<String> intentNames, List<String> prompts) {
            this.intentNames = intentNames;
            this.prompts = prompts;
            provided = false;
            this.maxIntentConfidence = 0.0;
            this.maxIntentName = "";
        }
    }

    //// Constants ////
    public static List<String> genericResponses;
    public static List<String> convoOverResponses;
    public static List<String> notUnderstandResponses;
    ///////////////////

    public static Set<String> lightIntents;
    public static Set<String> humidityIntents;
    public static Set<String> flowerIntents;

    public static Map<String, String> intentSearchMap;
    public static Map<String, List<String>> intentResponseMap;

    public static Map<String, IntentGroup> getNewIntentGroups() {
        Map<String, IntentGroup> intentGroups = new HashMap<String, IntentGroup>();

        List<String> lightIntentQuestions = new ArrayList<String>();
        lightIntentQuestions.add("What kind of light will your plants get?");
        lightIntentQuestions.add("What kind of light do you think your plants will get?");
        intentGroups.put("light", new IntentGroup(lightIntents, lightIntentQuestions));
        List<String> flowerIntentQuestions = new ArrayList<String>();
        flowerIntentQuestions.add("Do you want flowers on your plant? Or would you prefer just foliage?");
        flowerIntentQuestions.add("Would you like flowers or no flowers on your plant?");
        intentGroups.put("flower", new IntentGroup(flowerIntents, flowerIntentQuestions));
        List<String> humidityIntentQuestions = new ArrayList<String>();
        humidityIntentQuestions.add("Describe the humidity in your area.");
        humidityIntentQuestions.add("What is the humidity like in your area?");
        intentGroups.put("humidity", new IntentGroup(humidityIntents, humidityIntentQuestions));

        return intentGroups;
    }

    // Initialize static variables
    static {
        genericResponses = new ArrayList<String>();
        genericResponses.add("\nTell me something about the plant you're looking for or " +
                "the environment it will be in. For now, you can talk about humidity, flowers, or sunlight!");
        genericResponses.add("\nTell me about what kind of plant you want or " +
                "the type of environment it will be in. You can talk about humidity, flowers, or sunlight!");

        convoOverResponses = new ArrayList<String>();
        convoOverResponses.add("\nLet's find you the perfect plant...");
        convoOverResponses.add("\nYour plants are coming up :)...");

        notUnderstandResponses = new ArrayList<String>();
        notUnderstandResponses.add("I didn't understand.");
        notUnderstandResponses.add("Sorry your response didn't make sense to me.");
        notUnderstandResponses.add("Sorry, I’m afraid I don’t follow you.");

        lightIntents = new HashSet<String>();
        lightIntents.add("bright_light");
        lightIntents.add("direct_light");
        lightIntents.add("low_light");

        flowerIntents = new HashSet<String>();
        flowerIntents.add("has_flowers");
        flowerIntents.add("not_have_flowers");
        flowerIntents.add("Cactus_succulent");

        humidityIntents = new HashSet<String>();
        humidityIntents.add("low_humidity");
        humidityIntents.add("medium_humidity");
        humidityIntents.add("high_humidity");

        intentSearchMap = new HashMap<String, String>();
        intentSearchMap.put("low_humidity", "low");
        intentSearchMap.put("medium_humidity", "medium");
        intentSearchMap.put("high_humidity", "high");
        intentSearchMap.put("low_light", "low");
        intentSearchMap.put("bright_light", "bright");
        intentSearchMap.put("direct_light", "direct");
        intentSearchMap.put("has_flowers", "flowering");
        intentSearchMap.put("not_have_flowers", "foliage");
        intentSearchMap.put("Cactus_succulent", "cactus_succ");

        // TODO: add more responses
        intentResponseMap = new HashMap<String, List<String>>();
        List<String> low_humidity_responses = new ArrayList<String>();
        low_humidity_responses.add("Sounds like you have some dry air!");
        low_humidity_responses.add("Deserts are fun.");
        low_humidity_responses.add("Pretty dry, Huh?");
        intentResponseMap.put("low_humidity", low_humidity_responses);

        List<String> medium_humidity_responses = new ArrayList<String>();
        medium_humidity_responses.add("Ah, standard air.");
        medium_humidity_responses.add("Nice, normal humidity.");
        medium_humidity_responses.add("Pretty standard I see.");
        intentResponseMap.put("medium_humidity", medium_humidity_responses);

        List<String> high_humidity_responses = new ArrayList<String>();
        high_humidity_responses.add("Your air is wet.");
        high_humidity_responses.add("Living in the jungle I see!");
        high_humidity_responses.add("Wow you must have soft skin living in such high humidity.");
        intentResponseMap.put("high_humidity", high_humidity_responses);

        List<String> low_light_responses = new ArrayList<String>();
        low_light_responses.add("I get it. You want a sneaky spy plant that lives in the shadows.");
        low_light_responses.add("So your room doesn't get very much light, some plants like that surprisingly!");
        low_light_responses.add("Gotta love the shade sometimes.");
        intentResponseMap.put("low_light", low_light_responses);

        List<String> bright_light_responses = new ArrayList<String>();
        bright_light_responses.add("Light that's bright as your future!");
        bright_light_responses.add("Natural light is great for a room.");
        intentResponseMap.put("bright_light", bright_light_responses);

        List<String> direct_light_responses = new ArrayList<String>();
        direct_light_responses.add("A straight path to the sun.");
        direct_light_responses.add("You might need to wear sunglasses to get out of that direct sunlight.");
        direct_light_responses.add("I'll try to find a plant that doesn't burn from direct sunlight.");
        intentResponseMap.put("direct_light", direct_light_responses);

        List<String> has_flowers_responses = new ArrayList<String>();
        has_flowers_responses.add("So, you want some colorful flowers.");
        has_flowers_responses.add("I also love flowers.");
        intentResponseMap.put("has_flowers", has_flowers_responses);

        List<String> not_have_flowers_responses = new ArrayList<String>();
        not_have_flowers_responses.add("You're more of a leaf person. I get it.");
        not_have_flowers_responses.add("So you only want leaves on your plant, that's great.");
        not_have_flowers_responses.add("Sometimes foliage is just as pretty as flowers!");
        intentResponseMap.put("not_have_flowers", not_have_flowers_responses);

        List<String> cactus_succulent_responses = new ArrayList<String>();
        not_have_flowers_responses.add("Perhaps something prickly then?");
        not_have_flowers_responses.add("Maybe a cactus or a succulent - they're pretty easy.");
        intentResponseMap.put("Cactus_succulent", not_have_flowers_responses);

        List<String> end_conversation_responses = new ArrayList<String>();
        end_conversation_responses.add("Alright, we're done here.");
        end_conversation_responses.add("I see you want to be done, we will move on.");
        intentResponseMap.put("end_conversation", end_conversation_responses);

        List<String> non_answer_responses = new ArrayList<String>();
        non_answer_responses.add("Ok, let's move on.");
        non_answer_responses.add("That's alright, no one has all the answers.");
        intentResponseMap.put("non_answer", non_answer_responses);
    }
}
