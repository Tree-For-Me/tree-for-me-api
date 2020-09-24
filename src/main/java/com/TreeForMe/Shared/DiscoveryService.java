package com.TreeForMe.Shared;

import com.ibm.cloud.sdk.core.security.Authenticator;
import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.discovery.v1.Discovery;
import com.ibm.watson.discovery.v1.model.*;

import java.util.AbstractMap;
import java.util.List;

public final class DiscoveryService {

    private static DiscoveryService discoveryService;

    private Discovery discovery;

    private final String apiKey = "f6Vd5ZkUO2A3hmwhO2oSl6ZGUrS-al_Ze9MKS6nYotEC";
    private final String version = "2019-04-30";
    private final String serviceUrl = "https://api.us-south.discovery.watson.cloud.ibm.com/instances/4649ba97-b0ee-427b-a9c8-5ded79211b4e";
    private final String environmentId = "fb6af9b1-75c4-40f4-a03a-5ab0d33e6456";
    private final String collectionId = "2cc8aa00-e6de-4c87-a354-25a5bec22034";

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

    public String getDocTitleFromKeywordSearch(List<String> keywords) {
        // Build query
        StringBuilder query = new StringBuilder();
        for(String kw:keywords) {
            query.append("text:\"").append(kw).append("\",");
        }
        // Remove trailing comma
        query.deleteCharAt(query.length()-1);

        List<QueryResult> qrs = runQuery(query.toString());

        if(qrs.isEmpty()) {
            return "";
        }

        // Ensure we choose a result with a title
        QueryResult bestResult = qrs.get(0);
        int resultIndex = 0;
        while(!bestResult.getPropertyNames().contains("extracted_metadata")
                || !((AbstractMap)bestResult.get("extracted_metadata")).containsKey("title")) {
            resultIndex++;
            if(resultIndex >= qrs.size()) {
                // no results had a title
                return "";
            }
            bestResult = qrs.get(resultIndex);
        }

        return (String)((AbstractMap)bestResult.get("extracted_metadata")).get("title");
    }

    public static DiscoveryService getInstance() {
        if (discoveryService == null) {
            discoveryService = new DiscoveryService();
        }

        return discoveryService;
    }
}