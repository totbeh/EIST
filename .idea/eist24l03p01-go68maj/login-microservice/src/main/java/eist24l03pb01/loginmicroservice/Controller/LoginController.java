package eist24l03pb01.loginmicroservice.Controller;

import eist24l03pb01.loginmicroservice.Tweet;
import eist24l03pb01.loginmicroservice.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/login")
public class LoginController {
    @PostMapping("/performLogin")
    public String authenticateUser(@RequestBody User user) {
        if ("user".equals(user.getUserName()) && "passw".equals(user.getPassword())) {
            return "Login was successful!";
        } else {
            return "Login unsuccessful! Invalid credentials.";
        }
    }

}
