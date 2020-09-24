package com.TreeForMe.Shared;

import com.ibm.cloud.sdk.core.security.Authenticator;
import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.discovery.v1.Discovery;

public final class DiscoveryService {

    private static DiscoveryService discoveryService;

    private String apiKey = "enter key here";
    private String version = "2019-04-30";
    private String serviceUrl = "https://api.us-east.discovery.watson.cloud.ibm.com";

    private DiscoveryService() {
        IamAuthenticator authenticator = new IamAuthenticator(apiKey);
        Discovery discovery = new Discovery(version, authenticator);
        discovery.setServiceUrl(serviceUrl);
    }

    public static DiscoveryService getInstance() {

        if (discoveryService == null) {
            discoveryService = new DiscoveryService();
        }

        return discoveryService;
    }
}