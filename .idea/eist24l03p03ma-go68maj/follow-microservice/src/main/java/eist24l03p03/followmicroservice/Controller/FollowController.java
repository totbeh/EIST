package eist24l03p03.followmicroservice.Controller;

import eist24l03p03.followmicroservice.FollowRequest;
import eist24l03p03.followmicroservice.User;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(value = "/follow")
public class FollowController {
    private static final String activityURL = "http://localhost:8084/activity";
    HttpHeaders headers = new HttpHeaders();
    private RestTemplate restTemplate = new RestTemplate();

    @PostMapping("/follow")
    public String follow(@RequestBody FollowRequest followRequest) {
        User followedUser = followRequest.getFollowed();
        User followerUser = followRequest.getFollower();

        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<FollowRequest> requestEntity = new HttpEntity<>(followRequest, headers);
        ResponseEntity<Void> responseEntity = restTemplate.postForEntity(activityURL + "/addFollower", requestEntity, Void.class);
        return followedUser.getUserName() + " is followed!";
    }

    @DeleteMapping("/unfollow")
    public String unfollow(@RequestBody FollowRequest followRequest) {
        User followedUser = followRequest.getFollowed();
        User followerUser = followRequest.getFollower();

        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<FollowRequest> requestEntity = new HttpEntity<>(followRequest, headers);
        ResponseEntity<Void> responseEntity = restTemplate.exchange(activityURL + "/deleteFollower", HttpMethod.DELETE, requestEntity, Void.class);
        return followedUser.getUserName() + " is unfollowed!";
    }
}