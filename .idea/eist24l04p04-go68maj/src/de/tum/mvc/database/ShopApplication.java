package de.tum.mvc.database;

import de.tum.mvc.database.Controller.Controller;
import de.tum.mvc.database.Model.DatabaseHelper;
import de.tum.mvc.database.Model.Product;
import de.tum.mvc.database.View.MainView;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.List;


public class ShopApplication extends Application {

    public static void startApp(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        DatabaseHelper databaseHelper = new DatabaseHelper("jdbc:sqlite:pingu.db");
        databaseHelper.initializeDatabase();
        Controller controller = new Controller(databaseHelper);

        List<Product> products = controller.getProducts();

        if (products.isEmpty()) {
            Product product = new Product("Apples", "1kg of Apples, fresh", 3.44, 10);
            controller.saveProduct(product);
            product = new Product("Oranges", "500g of Oranges, fresh", 5.44, 6);
            controller.saveProduct(product);
            product = new Product("TUM T-shirt", "Most excellent T-shirt in the world", 18.68, 42);
            controller.saveProduct(product);
            products = controller.getProducts();
        }

        MainView mainView = new MainView(controller, products);
        mainView.show();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            databaseHelper.closeConnection();
            System.out.println("Database connection closed.");
        }));
    }

}
