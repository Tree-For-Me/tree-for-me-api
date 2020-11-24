package com.TreeForMe.Shared;

import com.google.common.io.Resources;
import com.google.gson.stream.JsonReader;
import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.cloud.sdk.core.service.exception.BadRequestException;
import com.ibm.cloud.sdk.core.util.GsonSingleton;

import com.ibm.watson.personality_insights.v3.PersonalityInsights;
import com.ibm.watson.personality_insights.v3.model.Content;
import com.ibm.watson.personality_insights.v3.model.Profile;
import com.ibm.watson.personality_insights.v3.model.ProfileOptions;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;


public final class PersonalityService {

    private static PersonalityService personalityService;

    private PersonalityInsights personalityInsights;

    private final String apiKey = "TONAML8VZYkZnnFDl34sfzBPo3FZaWTt9tedB-1SvR80";
    private final String version = "2020-11-19";
    private final String serviceUrl = "https://api.us-south.personality-insights.watson.cloud.ibm.com/instances/b2882aa6-3181-488f-8f65-ba783aa72f68";

    private PersonalityService() {
        IamAuthenticator authenticator = new IamAuthenticator(apiKey);
        personalityInsights = new PersonalityInsights(version, authenticator);
        personalityInsights.setServiceUrl(serviceUrl);
    }

    public Profile getPersonalityProfile(String text) {
        Profile profile = null;
        try {
            ProfileOptions profileOptions = new ProfileOptions.Builder()
                    .text(text)
                    .consumptionPreferences(true)
                    .rawScores(true)
                    .build();

            profile = personalityInsights.profile(profileOptions).execute().getResult();
            System.out.println(profile);
        } catch (BadRequestException e) {
            e.printStackTrace();
            // User input was less than 100 words so the IBM service threw an error, return something?
            System.out.println("Number of words from tweets was less than 100, cannot retrieve output.");
        }

        return profile;
    }

    public static PersonalityService getInstance() {
        if (personalityService == null) {
            personalityService = new PersonalityService();
        }

        return personalityService;
    }
}