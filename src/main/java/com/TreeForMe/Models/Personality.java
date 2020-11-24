package com.TreeForMe.Models;

import com.TreeForMe.Shared.DiscoveryService;
import com.ibm.watson.personality_insights.v3.model.Profile;

import java.util.ArrayList;
import java.util.List;

public class Personality {

    private String name;
    private double openness;
    private double conscientiousness;
    private double extraversion;
    private double agreeableness;
    private double neuroticism;

    private Personality(String name, double openness, double conscientiousness, double extraversion, double agreeableness, double neuroticism) {
        this.name = name;
        this.openness = openness;
        this.conscientiousness = conscientiousness;
        this.extraversion = extraversion;
        this.agreeableness = agreeableness;
        this.neuroticism = neuroticism;
    }

    public Personality(Profile prof) {

        //TODO: extract big 5 percentiles from profile object
        this.name = "user";
    }

    public static List<Personality> loadPlantPersonalities() {
        List<Personality> plantPersonalities = new ArrayList<>();

        //TODO: load from json

        return plantPersonalities;
    }

    public static Plant getClosestPlantName(Personality userPersonality, List<Personality> plantPersonalities) {
        if(plantPersonalities.isEmpty()) {
            return null;
        }

        // find closest plant
        Personality closest = null;
        double smallestDist = Double.MAX_VALUE;
        for(Personality p : plantPersonalities) {
            double dist = Math.pow(userPersonality.getOpenness() - p.getOpenness(), 2) +
                            Math.pow(userPersonality.getConscientiousness() - p.getConscientiousness(), 2) +
                            Math.pow(userPersonality.getExtraversion() - p.getExtraversion(), 2) +
                            Math.pow(userPersonality.getAgreeableness() - p.getAgreeableness(), 2) +
                            Math.pow(userPersonality.getNeuroticism() - p.getNeuroticism(), 2);
            if(dist < smallestDist) {
                smallestDist = dist;
                closest = p;
            }
        }

        // replace underscores with spaces
        String botName = closest.getName().replace('_', ' ');

        //returns null if no plant was found
        return DiscoveryService.getInstance().getPlantFromBotanicalName(botName);

        //plant name in json file is "botanical name" in discovery service (replace underscores with spaces)
    }


    String getName() {return this.name;}
    double getOpenness() {return this.openness;}
    double getConscientiousness() {return this.conscientiousness;}
    double getExtraversion() {return this.extraversion;}
    double getAgreeableness() {return this.agreeableness;}
    double getNeuroticism() {return this.neuroticism;}


}
