package eist24l03p03.messagebroker.Controller;

import eist24l03p03.messagebroker.Tweet;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.concurrent.*;

@RestController
@RequestMapping(value = "/mb")
public class MBController {
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    HttpHeaders headers = new HttpHeaders();
    private RestTemplate restTemplate = new RestTemplate();
    private Map<Integer, String> responses = new ConcurrentHashMap<>();//var to store the responses from a microservice
    private Map<Integer, Integer> userTweetCount = new ConcurrentHashMap<>();
    private BlockingQueue<Tweet> tweetQueue = new LinkedBlockingQueue<>();
    private String sendTweetURL = "http://localhost:8081/tweet/send";
    private final CompletableFuture<Void> processingFuture = CompletableFuture.runAsync(this::processTweets, executorService);

    @PostMapping(value = "/tweet/send")
    public CompletableFuture<String> sendTweet(@RequestBody Tweet tweet) {
        CompletableFuture<String> future = new CompletableFuture<>();

        // Enqueue the tweet
        tweetQueue.offer(tweet);
        // Respond immediately with a placeholder message
        future.complete("Tweet request received. Processing...");
        return future;
    }

    // Method to simulate background processing of tweets
    private void processTweets() {
        new Thread(() -> {
            while (true) {
                try {
                    // Dequeue tweets and process them
                    Tweet tweet = tweetQueue.take();
                    int userId = tweet.getUser().getUserID();
                    userTweetCount.putIfAbsent(userId, 0);

                    if ((userTweetCount.containsKey(userId) && userTweetCount.get(userId) < 5)) {
                        headers.setContentType(MediaType.APPLICATION_JSON);
                        HttpEntity<Tweet> requestEntity = new HttpEntity<>(tweet, headers);
                        ResponseEntity<String> responseEntity = restTemplate.postForEntity(sendTweetURL, requestEntity, String.class);
                        //ResponseEntity<String> responseEntity = new ResponseEntity<>("Tweet is sent",HttpStatusCode.valueOf(200));
                        if (responseEntity.getStatusCode() == HttpStatusCode.valueOf(200))
                            userTweetCount.replace(userId, 1 + userTweetCount.get(userId));
                        responses.putIfAbsent(tweet.getTweetID(), responseEntity.getBody().toString());
                    } else {
                        responses.putIfAbsent(tweet.getTweetID(), "You finished your daily Tweet limit");
                    }
                    notifyUser(tweet);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    //Logic to notify user after the async process is done by the thread. It can be a pop-up window or any type of communication. We will use a basic print
    //in message broker for now to sake of simplicity of message broker.
    public void notifyUser(Tweet tweet) {
        System.out.println(responses.get(tweet.getTweetID()));
    }

    public Map<Integer, String> getResponses() {
        return responses;
    }

    public void setResponses(Map<Integer, String> responses) {
        this.responses = responses;
    }

    public BlockingQueue<Tweet> getTweetQueue() {
        return tweetQueue;
    }

    public void setTweetQueue(BlockingQueue<Tweet> tweetQueue) {
        this.tweetQueue = tweetQueue;
    }
}