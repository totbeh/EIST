package eist24l03p03.messagebroker;

public class Tweet {
    private int tweetID;
    private String body;
    private User user;

    public Tweet(String body, User user, int tweetID) {
        this.body = body;
        this.user = user;
        this.tweetID = tweetID;
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
