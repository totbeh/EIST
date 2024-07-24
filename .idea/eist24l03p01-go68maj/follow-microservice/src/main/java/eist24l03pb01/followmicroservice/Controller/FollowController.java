package eist24l03pb01.followmicroservice.Controller;

import eist24l03pb01.followmicroservice.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/follow")
public class FollowController {

    @PostMapping("/follow")
    public String follow(@RequestBody User user) {
        return user.getUserName() + " is followed!";
    }

    @DeleteMapping("/unfollow")
    public String unfollow(@RequestBody User user) {
        return user.getUserName() + " is unfollowed!";
    }
}