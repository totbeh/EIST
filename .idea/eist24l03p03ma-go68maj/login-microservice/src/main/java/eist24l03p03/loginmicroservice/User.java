package eist24l03p03.loginmicroservice;

public class User {
    private static int nextId = 1;
    private String userName;
    private String password;
    private int userID;

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
        this.userID = getNextId();
    }

    private static synchronized int getNextId() {
        return nextId++;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
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
}
