package eist24l03p03.loginmicroservice.Controller;

import eist24l03p03.loginmicroservice.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/login"})
public class LoginController {
    public LoginController() {
    }

    @PostMapping({"/performLogin"})
    public String authenticateUser(@RequestBody User user) {
        return "user".equals(user.getUserName()) && "passw".equals(user.getPassword()) ? "Login was succesful!" : "Login unsuccesful! Invalid credentials.";
    }
}
