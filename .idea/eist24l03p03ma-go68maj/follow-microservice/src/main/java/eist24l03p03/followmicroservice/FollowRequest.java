package eist24l03p03.followmicroservice;

public class FollowRequest {
    private User follower;
    private User followed;

    public FollowRequest() {
    }

    public FollowRequest(User follower, User followed) {
        this.follower = follower;
        this.followed = followed;
    }

    public User getFollower() {
        return follower;
    }

    public void setFollower(User follower) {
        this.follower = follower;
    }

    public User getFollowed() {
        return followed;
    }

    public void setFollowed(User followed) {
        this.followed = followed;
    }
}
