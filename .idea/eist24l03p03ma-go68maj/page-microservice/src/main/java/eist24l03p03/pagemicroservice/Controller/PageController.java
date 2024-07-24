package eist24l03p03.pagemicroservice.Controller;

import eist24l03p03.pagemicroservice.User;
import eist24l03p03.pagemicroservice.Tweet;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/page")
public class PageController {
    private static final String getActivityURL = "http://localhost:8084/activity/getActivity/";
    private static final String getFollowedListURL = "http://localhost:8084/activity/getFollowedList/";
    HttpHeaders headers = new HttpHeaders();
    // TODO: implement the methods of this class. Make a use of the provided data structures
    private RestTemplate restTemplate = new RestTemplate();


    // page of one user
    @GetMapping("/getTimeLine/{id}")
    public List<Tweet> getTimeLine(@PathVariable("id") int userID) {
        String url = getActivityURL+userID;
        headers.setContentType(MediaType.APPLICATION_JSON);
        List<Tweet> response = restTemplate.getForObject(url, List.class);
        return response;
    }

    // pages of the followed users, i.g. feed
    @GetMapping("/getHomePage/{id}")
    public List<Tweet> getHomePage(@PathVariable("id") int userID) {
        String url = getFollowedListURL+userID;
        headers.setContentType(MediaType.APPLICATION_JSON);
        List<User> response = restTemplate.getForObject(url, List.class);
        List<Tweet> homePage = new ArrayList<>();
        for (User user: response) {
            for (Tweet tweet: getTimeLine(user.getUserID())) {
                homePage.add(tweet);
            }
        }

        return homePage;
    }
}