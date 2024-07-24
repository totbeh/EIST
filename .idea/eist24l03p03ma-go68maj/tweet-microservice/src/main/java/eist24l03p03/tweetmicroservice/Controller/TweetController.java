package eist24l03p03.tweetmicroservice.Controller;

import eist24l03p03.tweetmicroservice.Tweet;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(value = "/tweet")
public class TweetController {
    private static final String activityURL = "http://localhost:8084/activity";
    HttpHeaders headers = new HttpHeaders();
    private RestTemplate restTemplate = new RestTemplate();

    @DeleteMapping("/delete")
    public String deleteTweet(@RequestBody Tweet tweet) {
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Tweet> requestEntity = new HttpEntity<>(tweet, headers);
        ResponseEntity<Void> responseEntity = restTemplate.exchange(activityURL + "/deleteActivity", HttpMethod.DELETE, requestEntity, Void.class);
        return "The tweet: \n'''" + tweet.getBody() + "'''\nis deleted";
    }

    @PostMapping(value = "/send")
    public CompletableFuture<String> sendTweet(@RequestBody Tweet tweet) {
        return CompletableFuture.supplyAsync(() -> {
            saveToDb(tweet);
            return ("The tweet is sent");
        });

    }

    // Database operations will take time to process. So controller method should work async.
    public void saveToDb(Tweet tweet) {
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Tweet> requestEntity = new HttpEntity<>(tweet, headers);
        ResponseEntity<Void> responseEntity = restTemplate.postForEntity(activityURL + "/addActivity", requestEntity, Void.class);
        System.out.print(tweet.getBody());
    }
}


