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

        AssistantService as = new AssistantService();
        AssistantResponse ar = as.getResponse("");
        System.out.println(ar.getMessages());
        System.out.println(ar.getIntents());
        ar = as.getResponse("super dry");
        System.out.println(ar.getMessages());
        System.out.println(ar.getIntents());

        this.content = "";
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}