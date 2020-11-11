package com.TreeForMe.Shared;

import com.ibm.cloud.sdk.core.security.Authenticator;
import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.assistant.v2.Assistant;
import com.ibm.watson.assistant.v2.model.*;

import com.TreeForMe.Models.*;
import com.TreeForMe.Models.AssistantResponse.Intent;
import com.TreeForMe.Shared.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

public final class AssistantService {

    private Assistant assistant;
    private static AssistantService as;

    private final String apiKey = "1WzRl_MoopLm58UlW1fSCslmHiwBVo0uI2YD2OKwsd9J";
    private final String version = "2020-09-24";
    private final String serviceUrl = "https://api.us-south.assistant.watson.cloud.ibm.com/instances/45e166c6-5b68-4bcb-b783-69fa37178036";
    private final String assistantID = "54c951c6-f58d-48c6-bb3d-ea2eed4b9453";
    private String sessionId;

    private Map<Integer, Conversation> convos = new HashMap<>();

    private AssistantService() {
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

    public static AssistantService getInstance() {
        if (as == null) {
            as = new AssistantService();
        }

        return as;
    }

    public AssistantResponse getResponse(String userInput) {
        MessageInput input = new MessageInput.Builder()
                .messageType("text")
                .text(userInput)
                .options(new MessageInputOptions.Builder().returnContext(true).build())
                .build();

        MessageOptions options = new MessageOptions.Builder(this.assistantID, this.sessionId)
                .input(input)
                .build();

        // send input to watson
        MessageResponse response = assistant.message(options).execute().getResult();

        // get intents as context variable
        List<Map<String, Object>> intents = (List<Map<String, Object>>)response.getContext().skills().get("main skill").userDefined().get("myintents");

        // extract returned intents
        AssistantResponse ar = new AssistantResponse();
        for (Map<String, Object> intent : intents) {
            ar.addIntent((String)intent.get("intent"), (Double)intent.get("confidence"));
        }

        return ar;
    }

    public ResponseEntity<Message> getAssistantResponse(Message userMessage) {
        int userid = userMessage.getUser();
        String userMessageContent = userMessage.getMessageContent();
        String returnMessage;

        if (userid == -1) {
            // find a userid that doesn't already exist
            do {
                userid++;
            } while (convos.containsKey(userid));

            // make a new assistant service session and new plantInfo object
            convos.put(userid, new Conversation());

            returnMessage = "Welcome to Tree For Me. I'm going to help you find the perfect plant!\nTell me something about the plant you're looking for or the environment it will be in. For now, you can talk about humidity, flowers, or sunlight!";
        }
        else {
            // ensure userid is valid
            if (!convos.containsKey(userid)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

            if (userMessageContent.length() > 2048) {
                returnMessage = "Text cannot be longer than 2048 characters, stop trying to break our backend!";
            } else {
                AssistantResponse ar = AssistantService.getInstance().getResponse(userMessageContent);
                returnMessage = convos.get(userid).handleResponse(ar);
            }

        }

        if (convos.get(userid).isFinished()) {
            userid = -2;
        }

        return ResponseEntity.ok(new Message(returnMessage, userid));
    }


    public Map<Integer, Conversation> getConvos() {
        return convos;
    }

}