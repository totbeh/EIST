package eist24l03p03.activitymicroservice;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Tweet {
    @JsonProperty("tweetID")
    private int tweetID;

    private String body;
    private User user;

    public Tweet(String body, User user, int TweetId) {
        this.body = body;
        this.user = user;
        this.tweetID = TweetId;
    }

    public Tweet() {
    }

    public int getTweetID() {
        return tweetID;
    }

    public void setTweetID(int tweetID) {
        this.tweetID = tweetID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String text) {
        this.body = text;
    }
}
