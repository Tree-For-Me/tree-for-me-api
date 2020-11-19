package com.TreeForMe.Shared;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class TwitterService {

    private static TwitterService twitterService;

    private Twitter twitter;

    private static final String apiKey = "6cVL4Y298DI49LmGjwe5uKBTP";
    private static final String apiSecret = "toACanLnvMYs5X8J8yYEAIizcgDrdZaOvKmt3g6fPzlTjox7nL";
    private static final String accessToken = "19556667-xFj0o3pe6ky2mQi8Zi8oIYGjT6eM0NekgVJXvQaDE";
    private static final String accessTokenSecret = "whr1npo46jeoe6wFthu1Ga2jHQm5aY985pRsorjqiskeV";


    private TwitterService() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(apiKey)
                .setOAuthConsumerSecret(apiSecret)
                .setOAuthAccessToken(accessToken)
                .setOAuthAccessTokenSecret(accessTokenSecret);
        TwitterFactory tf = new TwitterFactory(cb.build());
        this.twitter = tf.getInstance();
    }

    public static TwitterService getInstance() {
        if (twitterService == null) {
            twitterService = new TwitterService();
        }

        return twitterService;
    }

    public String getUserTweetText(String userHandle) {
        Query query = new Query("from:" + userHandle);
        query.setCount(20);
        QueryResult result = null;
        try {
            result = twitter.search(query);
        } catch (TwitterException e) {
            e.printStackTrace();
        }

        StringBuilder tweetTextBuilder = new StringBuilder();

        for (Status status : result.getTweets()) {
            if (!status.isRetweet()) {
                tweetTextBuilder.append(status.getText()).append("\n");
            }
        }

        String tweetText = tweetTextBuilder.toString();

        /* Remove url links */
        tweetText = tweetText.replaceAll("http.*?\\s", "");

        return tweetText;
    }



}