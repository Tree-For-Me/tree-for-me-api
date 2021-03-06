package com.TreeForMe.Shared;

import com.TreeForMe.Models.Conversation;
import com.ibm.cloud.sdk.core.security.Authenticator;
import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.discovery.v1.Discovery;
import com.ibm.watson.discovery.v1.model.*;

import com.TreeForMe.Models.Plant;
import com.TreeForMe.Models.PlantInfo;

import java.util.*;

public final class DiscoveryService {

    private static DiscoveryService discoveryService;

    private Discovery discovery;

    private final String apiKey = "WyZD8g21UK7CtLQRgmpA7M7O5b7WC2jL3lCdc--EXrA6";
    private final String version = "2019-04-30";
    private final String serviceUrl = "https://api.us-south.discovery.watson.cloud.ibm.com/instances/5d00a7e4-609b-47c2-9d1d-33136d30f172";
    private final String environmentId = "f6f77231-b3f9-4da2-a58e-e3bb4792ffa0";
    private final String collectionId = "12ed6efa-a259-4681-8bcd-4f596ae67dbb";

    private DiscoveryService() {
        IamAuthenticator authenticator = new IamAuthenticator(apiKey);
        this.discovery = new Discovery(version, authenticator);
        this.discovery.setServiceUrl(serviceUrl);
    }

    /*
    public String listEnvs() {
        ListEnvironmentsOptions options = new ListEnvironmentsOptions.Builder().build();
        ListEnvironmentsResponse listResponse = this.discovery.listEnvironments(options).execute().getResult();
        return listResponse.toString();

    }

    public String listCollections() {
        ListCollectionsOptions listOptions = new ListCollectionsOptions.Builder(environmentId).build();
        ListCollectionsResponse listResponse = discovery.listCollections(listOptions).execute().getResult();
        return listResponse.toString();
    }
     */

    private List<QueryResult> runQuery(String queryText) {
        QueryOptions.Builder queryBuilder = new QueryOptions.Builder(environmentId, collectionId);
        queryBuilder.query(queryText);
        QueryResponse queryResponse = discovery.query(queryBuilder.build()).execute().getResult();
        return queryResponse.getResults();
    }

    public List<Plant> getPlantNameFromKeywordSearch(List<String> keywords) {
        // Build query
        StringBuilder query = new StringBuilder();
        //query.append("type:\"").append(keywords.get(0)).append("\",");
        for(int i = 0; i < keywords.size(); i++) {
            query.append("content:\"").append(keywords.get(i)).append("\",");
        }
        // Remove trailing comma
        query.deleteCharAt(query.length()-1);

        List<QueryResult> qrs = runQuery(query.toString());

        if(qrs.isEmpty()) {
            return new ArrayList<Plant>();
        }

        // Choose up to three best results
        int results = qrs.size() > 2 ? 3 : qrs.size();
        List<QueryResult> bestResults = qrs.subList(0, results);

        // List of plant objects to return
        List<Plant> bestPlants = new ArrayList<>();

        for (QueryResult result : bestResults) {
            String plantName = (String) result.get("page_title");
            String imageLink = (String) result.get("image");
            String botName = (String) result.get("botanical_name");
            bestPlants.add(new Plant(plantName, imageLink, botName, "#"));
        }

        return bestPlants;
    }

    public List<Plant> getPlantNameFromFieldSearch(Conversation currentConvo) {
        // Build query
        StringBuilder query = new StringBuilder();
        PlantInfo plantInfo = currentConvo.getPlantInfo();
        if (!plantInfo.getFlowers().isEmpty())
            query.append("type:\"").append(plantInfo.getFlowers()).append("\",");
        if (!plantInfo.getHumidity().isEmpty())
            query.append("humidity:\"").append(plantInfo.getHumidity()).append("\",");
        if (!plantInfo.getLight().isEmpty())
            query.append("light:\"").append(plantInfo.getLight()).append("\",");

        // Remove trailing comma
        if (query.length() > 0) {
            query.deleteCharAt(query.length() - 1);
        }

        List<Plant> bestPlants;
        // If it is not the first query and the query is the same then we don't need to redo the query
        if (currentConvo.getOldQuery() != null && currentConvo.getOldQuery().equals(query.toString())) {
            bestPlants = currentConvo.getOldBestPlants();
        } else { // Run a new query
            List<QueryResult> qrs = runQuery(query.toString());

            if (qrs.isEmpty()) {
                return new ArrayList<Plant>();
            }

            // Choose up to three best results
            int results = qrs.size() > 4 ? 5 : qrs.size();
            Collections.shuffle(qrs);
            List<QueryResult> bestResults = qrs.subList(0, results);

            // List of plant objects to return
            bestPlants = new ArrayList<>();

            for (QueryResult result : bestResults) {
                String plantName = (String) result.get("page_title");
                String imageLink = (String) result.get("image");
                String botName = (String) result.get("botanical_name");
                String careLink = (String) result.get("link");
                bestPlants.add(new Plant(plantName, imageLink, botName, careLink));
            }

            currentConvo.setOldBestPlants(bestPlants);
            currentConvo.setOldQuery(query.toString());
        }

        return bestPlants;
    }

    public static DiscoveryService getInstance() {
        if (discoveryService == null) {
            discoveryService = new DiscoveryService();
        }

        return discoveryService;
    }
}