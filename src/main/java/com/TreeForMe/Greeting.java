package com.TreeForMe;

import com.TreeForMe.Models.AssistantResponse;
import com.TreeForMe.Models.Message;
import com.TreeForMe.Shared.AssistantService;
import com.TreeForMe.Shared.DiscoveryService;
import com.TreeForMe.Shared.TwitterService;

import java.util.ArrayList;
import java.util.List;

public class Greeting {

    private final long id;
    private final String content;

    public Greeting(long id, String content) {
        this.id = id;
        this.content = "";

        TwitterService ts = TwitterService.getInstance();
        String tweets = ts.getUserTweetText("preskmjohnson");

        System.out.println(tweets);
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}