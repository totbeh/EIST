package EIST24L03PB01;

import com.fasterxml.jackson.databind.introspect.TypeResolutionContext;

import java.util.ArrayList;

public class User {
    private ArrayList<User> followers = new ArrayList<>();
    private String userName;
    private String password;

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addFollower(User user) {
        followers.add(user);
    }

    public void removeFollower(User user) {
        followers.remove(user);
    }
}
