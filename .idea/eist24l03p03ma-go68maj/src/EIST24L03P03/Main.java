package EIST24L03P03;

import EIST24L03P03.Client.Client;

import java.text.ParseException;

import java.util.Scanner;

public class Main {
    private final Client client = new Client();

    public static void main(String[] args) throws ParseException, InterruptedException {
        Client client1 = new Client();
        User testusr1 = new User("user", "passw");
        System.out.println(client1.performLogin(testusr1));
        User testusr2 = new User("name2", "pwd2");
        User testusr3 = new User("name3", "pwd3");
        Tweet tweet1 = new Tweet("body1", testusr1, 1);
        Tweet tweet2 = new Tweet("body2", testusr1, 2);
        Tweet tweet3 = new Tweet("body3", testusr1, 3);
        Tweet tweet4 = new Tweet("body4", testusr1, 4);
        Tweet tweet5 = new Tweet("body5", testusr2, 5);
        Tweet tweet6 = new Tweet("body6", testusr2, 6);
        Tweet tweet7 = new Tweet("body7", testusr3, 7);

        System.out.println(client1.follow(testusr2, testusr1));
        System.out.println(client1.follow(testusr2, testusr3));
        System.out.println(client1.follow(testusr3, testusr2));
        System.out.println(client1.sendTweet(tweet1));
        System.out.println(client1.sendTweet(tweet2));
        System.out.println(client1.sendTweet(tweet3));
        System.out.println(client1.sendTweet(tweet4));
        System.out.println(client1.sendTweet(tweet5));
        System.out.println(client1.sendTweet(tweet6));
        System.out.println(client1.sendTweet(tweet7));
        Thread.sleep(3000); // DO NOT REMOVE !!!
        client1.showTimeLine(testusr1);
        System.out.println("************");
        client1.showTimeLine(testusr2);
        System.out.println("************");
        client1.showHomePage(testusr2);
        System.out.println(client1.deleteTweet(tweet2));
        System.out.println(client1.deleteTweet(tweet3));
        client1.showHomePage(testusr2);
        System.out.println(client1.unfollow(testusr2, testusr1));
        client1.showHomePage(testusr2);

        //Main main = new Main();
        //main.operate();
    }

    public void operate() {
        /*Scanner scanner = new Scanner(System.in);

        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User user = new User(username, password);
        String loginResponse = client.performLogin(user);
        
        if (loginResponse.contains("successful")) {
            System.out.println("Login Successful!");


            System.out.print("Enter your tweet: ");
            String text= new String();
            text = scanner.nextLine();
            Tweet tweet;
            tweet = new Tweet(text);
            System.out.println(client.sendTweet(tweet));
            System.out.println("?Do you want to delete the tweet? Answer yes or no");

            if(scanner.nextLine().contains("yes")){
                client.deleteTweet(tweet);
            }

        } else {
            System.out.println("Login failed: " + loginResponse);
        }

        scanner.close();*/
    }
}
