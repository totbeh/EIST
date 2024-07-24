package EIST24L03PB01;

import EIST24L03PB01.Client.Client;

import java.text.ParseException;

import java.util.Scanner;

public final class Main {
    private final Client client = new Client();

    public static void main(String[] args) throws ParseException {
        Main main = new Main();
        main.operate();
    }

    public void operate() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User user = new User(username, password);
        String loginResponse = client.performLogin(user);

        if (loginResponse.contains("Login was successful!")) {
            System.out.println("Login Successful!");

            System.out.print("Enter your tweet: ");
            String text = scanner.nextLine();

            Tweet tweet = new Tweet(text);
            System.out.println(client.sendTweet(tweet));
            System.out.println("?Do you want to delete this tweet?\nAnswer 'yes' or 'no'");

            if (scanner.nextLine().contains("yes")) {
                System.out.println(client.deleteTweet(tweet));
            }

            User president = new User("Thomas Hofmann", "VeryStrongPassword");
            System.out.println("?Do you want to follow a user 'Thomas Hofmann'?\nAnswer 'yes' or 'no'");

            if (scanner.nextLine().contains("yes")) {
                System.out.println(client.follow(president));
                president.addFollower(user); // should be done in FollowController
            } else {
                System.out.println(client.unfollow(president));
                president.removeFollower(user); // should be done in FollowController
            }
        } else {
            System.out.println("Login failed: " + loginResponse);
        }
        scanner.close();
    }
}
