package com.TreeForMe;

import com.TreeForMe.Shared.DiscoveryService;

import java.util.ArrayList;
import java.util.List;

public class Greeting {

    private final long id;
    private final String content;

    public Greeting(long id, String content) {
        this.id = id;
        DiscoveryService disc = DiscoveryService.getInstance();
        List<String> keywords = new ArrayList<>();
        keywords.add("bright indirect");
        keywords.add("flowers");
        keywords.add("humid");
        keywords.add("fern");
        this.content = disc.getPlantNameFromKeywordSearch(keywords);
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}