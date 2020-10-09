package com.TreeForMe.Shared;

import com.ibm.cloud.sdk.core.security.Authenticator;
import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.discovery.v2.Assistant;
import com.ibm.watson.discovery.v1.model.*;

import java.util.AbstractMap;
import java.util.List;

public final class AssistantService {

    private static AssistantService assistantService;

    private Assistant assistant;

    private final String apiKey = "1WzRl_MoopLm58UlW1fSCslmHiwBVo0uI2YD2OKwsd9J";
    private final String version = "2019-04-30";
    private final String serviceUrl = "https://api.us-south.assistant.watson.cloud.ibm.com/instances/45e166c6-5b68-4bcb-b783-69fa37178036";
    private final String assistantID = "54c951c6-f58d-48c6-bb3d-ea2eed4b9453";
    private final String sessionId;

    private AssistantService() {
        IamAuthenticator authenticator = new IamAuthenticator(apiKey);
        this.assistant = new Assistant(version, authenticator);
        this.assistant.setServiceUrl(serviceUrl);
        this.createSession();
    }

    public createSession() {
        CreateSessionOptions options = new CreateSessionOptions.Builder(this.assistantID).build();
        SessionResponse response = assistant.createSession(options).execute().getResult();
        this.sessionId = session.getSessionId();
    }

    public deleteSession() {
        DeleteSessionOptions options = new DeleteSessionOptions.Builder("{assistant_id}", "{session_id}").build();
        assistant.deleteSession(options).execute();
        this.sessionid = "";
    }

    public Message getResponse(Message userInput) {
        MessageInput input = new MessageInput.Builder()
                .messageType("text")
                .text(userInput.messageContent)
                .build();

        MessageOptions options = new MessageOptions.Builder(this., assistant.getSess)
                .input(input)
                .build();

        MessageResponse response = assistant.message(options).execute().getResult();

        // This block is how the IBM walkthrough gets message text from this response
        String responseText;
        List<RuntimeResponseGeneric> responseGeneric = response.getOutput().getGeneric();
        if(responseGeneric.size() > 0) {
            if(responseGeneric.get(0).responseType().equals("text")) {
                responseText = responseGeneric.get(0).text();
            }
        }

        Message assistantResponse = new Message(responseText, "assistant");
    }

    public static DiscoveryService getInstance() {
        if (discoveryService == null) {
            discoveryService = new DiscoveryService();
        }

        return discoveryService;
    }
}