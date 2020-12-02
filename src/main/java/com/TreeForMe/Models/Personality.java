package com.TreeForMe.Models;

import com.TreeForMe.Shared.DiscoveryService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ibm.watson.personality_insights.v3.model.Profile;
import com.ibm.watson.personality_insights.v3.model.Trait;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Personality {

    private String name;
    private double openness;
    private double conscientiousness;
    private double extraversion;
    private double agreeableness;
    private double neuroticism;

    @JsonIgnore
    private static List<Personality> plantPersonalities = null;

    private Personality(String name, double openness, double conscientiousness, double extraversion, double agreeableness, double neuroticism) {
        this.name = name;
        this.openness = openness;
        this.conscientiousness = conscientiousness;
        this.extraversion = extraversion;
        this.agreeableness = agreeableness;
        this.neuroticism = neuroticism;
    }

    public Personality(Profile prof) {
        for(Trait t : prof.getPersonality()) {
            String traitId = t.getTraitId();
            if(traitId.equals("big5_openness")) {
                this.openness = t.getPercentile();
            }
            else if(traitId.equals("big5_conscientiousness")) {
                this.conscientiousness = t.getPercentile();
            }
            else if(traitId.equals("big5_extraversion")) {
                this.extraversion = t.getPercentile();
            }
            else if(traitId.equals("big5_agreeableness")) {
                this.agreeableness = t.getPercentile();
            }
            else if(traitId.equals("big5_neuroticism")) {
                this.neuroticism = t.getPercentile();
            }
        }
        this.name = "user";
    }

    private static void loadPlantPersonalities() {
        plantPersonalities = new ArrayList<>();

        // Obtain list of files in path
        File[] files = new File("src/main/resources/plant_personalities").listFiles();
        if(files == null) {
            return;
        }

        // Consider each plant personality file
        for(File file : files) {
            // read json into object
            JSONObject jsonObj = null;
            try {
                jsonObj = (JSONObject) new JSONParser().parse(new FileReader(file));
            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }

            // Remove file extension and replace underscores with spaces
            String filename = file.getName();
            String plantName = filename.substring(0, filename.lastIndexOf(".json")).replace('_', ' ');

            double openness = 0.0;
            double conscientiousness = 0.0;
            double extraversion = 0.0;
            double agreeableness = 0.0;
            double neuroticism = 0.0;

            // Consider each personality trait
            for(Object traitObj : (JSONArray) jsonObj.get("personality")) {
                JSONObject trait = (JSONObject) traitObj;
                String traitId = (String) trait.get("trait_id");

                if(traitId.equals("big5_openness")) {
                    openness = (double) trait.get("percentile");
                }
                else if(traitId.equals("big5_conscientiousness")) {
                    conscientiousness = (double) trait.get("percentile");
                }
                else if(traitId.equals("big5_extraversion")) {
                    extraversion = (double) trait.get("percentile");
                }
                else if(traitId.equals("big5_agreeableness")) {
                    agreeableness = (double) trait.get("percentile");
                }
                else if(traitId.equals("big5_neuroticism")) {
                    neuroticism = (double) trait.get("percentile");
                }
            }

            plantPersonalities.add(new Personality(plantName, openness, conscientiousness, extraversion, agreeableness, neuroticism));
        }
    }

    @JsonIgnore
    public Personality getClosestPlant() {
        if(plantPersonalities == null) {
            loadPlantPersonalities();
        }

        if(plantPersonalities.isEmpty()) {
            return null;
        }

        // find closest plant
        Personality closest = null;
        double smallestDist = Double.MAX_VALUE;
        for(Personality p : plantPersonalities) {
            double dist = Math.pow(this.openness - p.getOpenness(), 2) +
                            Math.pow(this.conscientiousness - p.getConscientiousness(), 2) +
                            Math.pow(this.extraversion - p.getExtraversion(), 2) +
                            Math.pow(this.agreeableness - p.getAgreeableness(), 2) +
                            Math.pow(this.neuroticism - p.getNeuroticism(), 2);
            if(dist < smallestDist) {
                smallestDist = dist;
                closest = p;
            }
        }

        return closest;
    }

    @JsonIgnore
    public static Plant getPlantFromPersonality(Personality p) {
        // replace underscores with spaces
        String botName = p.getName().replace('_', ' ');

        //returns null if no plant was found
        return DiscoveryService.getInstance().getPlantFromBotanicalName(botName);
    }


    public String getName() {return this.name;}
    public double getOpenness() {return this.openness;}
    public double getConscientiousness() {return this.conscientiousness;}
    public double getExtraversion() {return this.extraversion;}
    public double getAgreeableness() {return this.agreeableness;}
    public double getNeuroticism() {return this.neuroticism;}


}
