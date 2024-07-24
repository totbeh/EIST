package eist24l03p03.activitymicroservice.Controller;

import eist24l03p03.activitymicroservice.FollowRequest;
import eist24l03p03.activitymicroservice.Tweet;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import eist24l03p03.activitymicroservice.User;

@RestController
@RequestMapping("/activity")
public class ActivityController {
    // TODO: implement the methods of this class. Make a use of the provided data structures
    private Map<Integer, List<Tweet>> userActivityMap = new HashMap<>();
    private Map<Integer, List<User>> userFollowedMap = new HashMap<>();


    @PostMapping("/addActivity")
    public void addActivity(@RequestBody Tweet tweet) {
        int userId = tweet.getUser().getUserID();
        List<Tweet> tweetList = getActivity(userId);
        if (!userActivityMap.containsKey(userId)){
            tweetList.add(tweet);
            userActivityMap.put(userId,tweetList);
        }else userActivityMap.get(userId).add(tweet);
    }
    @PostMapping("/addFollower")
    public void addFollower(@RequestBody FollowRequest followRequest) {
        User follower = followRequest.getFollower();
        User followed = followRequest.getFollowed();
        if (userFollowedMap.get(follower.getUserID()) == null){
            List<User> list = new ArrayList<>();
            list.add(followed);
            userFollowedMap.put(follower.getUserID(),list);
            return;
        }
        List<User> list = userFollowedMap.get(follower.getUserID());
        list.add(followed);
        userFollowedMap.put(follower.getUserID(),list);
    }
    @GetMapping("/getActivity/{id}")
    public List<Tweet> getActivity(@PathVariable("id") int userID){
        return userActivityMap.getOrDefault(userID,new ArrayList<>());
    }

    @DeleteMapping("/deleteActivity")
    public void deleteActivity(@RequestBody Tweet tweet) {
        int userId = tweet.getUser().getUserID();
        if (userActivityMap.containsKey(userId)) {
            List<Tweet> tweets = userActivityMap.get(userId);
            tweets.removeIf(existingTweet -> existingTweet.getTweetID() == tweet.getTweetID());
        }
    }

    @DeleteMapping("/deleteFollower")
    public void deleteFollower(@RequestBody FollowRequest followRequest) {
        int follower = followRequest.getFollower().getUserID();
        User followed = followRequest.getFollowed();
        userFollowedMap.get(follower).remove(followed);
    }
    @GetMapping("/getFollowedList/{id}")
    public List<User> getFollowedList(@PathVariable("id") int userID) {
        return userFollowedMap.getOrDefault(userID,new ArrayList<>());
    }
}