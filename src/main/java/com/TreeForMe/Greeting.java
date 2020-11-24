package com.TreeForMe;

import com.TreeForMe.Models.AssistantResponse;
import com.TreeForMe.Models.Message;
import com.TreeForMe.Shared.AssistantService;
import com.TreeForMe.Shared.DiscoveryService;
import com.TreeForMe.Shared.PersonalityService;
import com.TreeForMe.Shared.TwitterService;
import com.ibm.watson.personality_insights.v3.model.Profile;

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

        PersonalityService ps = PersonalityService.getInstance();
        Profile profile = ps.getPersonalityProfile("ass");

    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}