package de.tum.mvc.database;

public final class ShopMain {
    private ShopMain() {

    }

    public static void main(String[] args) {
        //This is a workaround for a known issue when starting JavaFX applications
        // Run ShopMain.main() to run the application
        ShopApplication.startApp(args);
    }

}
