package de.tum.mvc.database.View;

import de.tum.mvc.database.Controller.Controller;
import de.tum.mvc.database.Model.Product;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.Pair;

import java.util.List;
import java.util.Optional;

public class CustomerView extends ParentView {

    public CustomerView(Controller controller, List<Product> productsList) {
        super(controller, productsList);
        controller.setCustomerView(this);
        HBox layout = generateUserInterface();
        getChildren().add(layout);
    }

    private HBox generateUserInterface() {
        ScrollPane scrollPane = new ScrollPane();
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        grid.getColumnConstraints().add(new ColumnConstraints(150));
        grid.getColumnConstraints().add(new ColumnConstraints(70));
        grid.getColumnConstraints().add(new ColumnConstraints(80));

        for (int i = 0; i < products.size(); i++) {
            Label productName = new Label(products.get(i).getName());
            Label productPrice = new Label(products.get(i).getPrice() + " Euro");
            Button buyButton = createBuyButton(products.get(i));

            grid.add(productName, 0, i + 1);
            grid.add(productPrice, 1, i + 1);
            grid.add(buyButton, 2, i + 1);

            GridPane.setHalignment(productName, HPos.LEFT);
            GridPane.setHalignment(productPrice, HPos.RIGHT);
            GridPane.setHalignment(buyButton, HPos.CENTER);

        }
        scrollPane.setContent(grid);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setPrefViewportWidth(500);

        HBox layout = new HBox(5, scrollPane);
        layout.setAlignment(Pos.CENTER);
        return layout;
    }

    private Button createBuyButton(Product product) {
        Button buyButton = new Button("Buy");

        buyButton.setOnAction(event -> {
            Dialog<Pair<String, Integer>> dialog = new Dialog<>();
            dialog.setTitle("Purchase Product");
            dialog.setHeaderText("Enter Quantity for " + product.getName());

            ButtonType buyButtonType = new ButtonType("Buy", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(buyButtonType, ButtonType.CANCEL);

            Spinner<Integer> quantitySpinner = new Spinner<>(1, 100, 1); // Adjust range as needed
            quantitySpinner.setEditable(true);

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.add(new Label("Quantity:"), 0, 0);
            grid.add(quantitySpinner, 1, 0);

            dialog.getDialogPane().setContent(grid);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == buyButtonType) {
                    return new Pair<>("buy", quantitySpinner.getValue());
                }
                return null;
            });

            Optional<Pair<String, Integer>> result = dialog.showAndWait();

            result.ifPresent(quantityResult -> {
                if ("buy".equals(quantityResult.getKey())) {
                    try {
                        controller.buyProduct(product, quantityResult.getValue());
                    } catch (Exception e) {
                        showAlert(Alert.AlertType.INFORMATION, "Insufficient stock for your order", e.getMessage());
                    }
                }
            });
        });
        return buyButton;
    }

    @Override
    public void update() {
        super.update();

        HBox layout = generateUserInterface();
        ObservableList<Node> children = getChildren();
        children.remove(children.size() - 1);
        children.add(layout);
    }
}
