package de.tum.mvc.database.View;

import de.tum.mvc.database.Controller.Controller;
import de.tum.mvc.database.Model.Product;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

import java.util.List;

public class MainView extends Stage {


    public MainView(Controller controller, List<Product> productsList) {
        TabPane tabPane = new TabPane();

        Tab customerTab = new Tab("Customer View");
        CustomerView customerView = new CustomerView(controller, productsList);
        customerTab.setContent(customerView);
        customerTab.setClosable(false);

        Tab adminTab = new Tab("Admin View");
        AdminView adminView = new AdminView(controller, productsList);
        adminTab.setContent(adminView);
        adminTab.setClosable(false);

        tabPane.getTabs().addAll(customerTab, adminTab);

        Scene scene = new Scene(tabPane, 600, 400);
        setTitle("Shopingu");
        setScene(scene);
    }
}