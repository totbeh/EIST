package eist24l03pb01.tweetmicroservice;

public class Tweet {
    private String body;

    public Tweet() {
    }

    public Tweet(String body) {
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String text) {
        this.body = text;
    }
}
