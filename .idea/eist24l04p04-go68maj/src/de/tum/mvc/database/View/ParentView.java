package de.tum.mvc.database.View;

import de.tum.mvc.database.Controller.Controller;
import de.tum.mvc.database.Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;

import java.util.List;

public class ParentView extends VBox implements Observer {

    protected final Controller controller;
    protected final ObservableList<Product> products;

    public ParentView(Controller controller, List<Product> productsList) {
        this.controller = controller;
        this.products = FXCollections.observableArrayList(productsList);
        for (Product product : products) {
            product.addObserver(this);
        }
    }

    // TODO: Implement addProduct() method
    public void addProduct(Product product) {
        int id = product.getId();
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId() == id) {
                products.remove(i);
                products.add(product);
                return;
            }
        }
        products.add(product);


    }

    protected void showAlert(Alert.AlertType alertType, String s, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(alertType.name());
        alert.setHeaderText(s);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // TODO: Implement update() method
    @Override
    public void update() {
        List<Product> productList = controller.getProducts();
        products.removeAll();
        for (Product product:productList) {
            products.add(product);
            product.addObserver(this);

        }
    }
}
