package com.TreeForMe;

import com.TreeForMe.Models.AssistantResponse;
import com.TreeForMe.Models.Message;
import com.TreeForMe.Shared.AssistantService;
import com.TreeForMe.Shared.DiscoveryService;

import java.util.ArrayList;
import java.util.List;

public class Greeting {

    private final long id;
    private final String content;

    public Greeting(long id, String content) {
        this.id = id;

        AssistantService as = AssistantService.getInstance();
        AssistantResponse ar = as.getResponse("it is bright in here and very humid");
        for(AssistantResponse.Intent intent : ar.getIntents()) {
            System.out.println(intent.getName());
            System.out.println(intent.getConfidence());
        }

        this.content = "";
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}