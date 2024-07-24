package EIST24L03PB01.Client;

import EIST24L03PB01.Tweet;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import EIST24L03PB01.User;

public class Client {
    private static final String loginMicroserviceURL = "http://localhost:8080/login";
    private static final String tweetMicroserviceURL = "http://localhost:8081/tweet";
    private static final String followMicroserviceURL = "http://localhost:8082/follow";
    HttpHeaders headers = new HttpHeaders();
    private RestTemplate restTemplate;

    public Client() {
        this.restTemplate = new RestTemplate();
    }

    public String performLogin(User user) {
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<User> requestEntity = new HttpEntity<>(user, headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(loginMicroserviceURL + "/performLogin", requestEntity, String.class);

        return responseEntity.getBody();
    }

    //TODO: Change the url endpoints from loginMicroservice to respective microservice for below methods.
    public String sendTweet(Tweet tweet) {
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Tweet> requestEntity = new HttpEntity<>(tweet, headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(tweetMicroserviceURL + "/send", requestEntity, String.class);

        return responseEntity.getBody();
    }

    public String deleteTweet(Tweet tweet) {
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Tweet> requestEntity = new HttpEntity<>(tweet, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(tweetMicroserviceURL + "/delete", HttpMethod.DELETE, requestEntity, String.class);

        return responseEntity.getBody();
    }

    public String follow(User user) {
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<User> requestEntity = new HttpEntity<>(user, headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(followMicroserviceURL + "/follow", requestEntity, String.class);

        return responseEntity.getBody();
    }

    public String unfollow(User user) {
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<User> requestEntity = new HttpEntity<>(user, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(followMicroserviceURL + "/unfollow", HttpMethod.DELETE, requestEntity, String.class);

        return responseEntity.getBody();
    }

}
