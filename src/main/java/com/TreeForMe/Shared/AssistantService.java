package com.TreeForMe.Shared;

import com.TreeForMe.Models.AssistantResponse;
import com.ibm.cloud.sdk.core.security.Authenticator;
import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.assistant.v2.Assistant;
import com.ibm.watson.assistant.v2.model.*;

import com.TreeForMe.Models.Message;

import java.util.AbstractMap;
import java.util.List;

public final class AssistantService {

    private Assistant assistant;

    private final String apiKey = "1WzRl_MoopLm58UlW1fSCslmHiwBVo0uI2YD2OKwsd9J";
    private final String version = "2019-04-30";
    private final String serviceUrl = "https://api.us-south.assistant.watson.cloud.ibm.com/instances/45e166c6-5b68-4bcb-b783-69fa37178036";
    private final String assistantID = "54c951c6-f58d-48c6-bb3d-ea2eed4b9453";
    private String sessionId;

    public AssistantService() {
        IamAuthenticator authenticator = new IamAuthenticator(apiKey);
        this.assistant = new Assistant(version, authenticator);
        this.assistant.setServiceUrl(serviceUrl);
        this.createSession();
    }

    public void createSession() {
        CreateSessionOptions options = new CreateSessionOptions.Builder(this.assistantID).build();
        SessionResponse response = assistant.createSession(options).execute().getResult();
        this.sessionId = response.getSessionId();
    }

    public void deleteSession() {
        DeleteSessionOptions options = new DeleteSessionOptions.Builder("{assistant_id}", "{session_id}").build();
        assistant.deleteSession(options).execute();
        this.sessionId = "";
    }

    public AssistantResponse getResponse(String userInput) {
        MessageInput input = new MessageInput.Builder()
                .messageType("text")
                .text(userInput)
                .build();

        MessageOptions options = new MessageOptions.Builder(this.assistantID, this.sessionId)
                .input(input)
                .build();

        // send input to watson
        MessageResponse response = assistant.message(options).execute().getResult();

        AssistantResponse ar = new AssistantResponse();

        // extract returned messages
        List<RuntimeResponseGeneric> responseGenerics = response.getOutput().getGeneric();
        for(RuntimeResponseGeneric rrg : responseGenerics) {
            if(rrg.responseType().equals("text")) {
                ar.addMessage(rrg.text());
            }
        }

        // extract returned intents
        List<RuntimeIntent> intents = response.getOutput().getIntents();
        for(RuntimeIntent ri : intents) {
            ar.addIntent(ri.intent());
        }

        return ar;
    }

}