package de.tum.mvc.database.View;

import de.tum.mvc.database.Controller.Controller;
import de.tum.mvc.database.Model.Product;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

import java.util.List;
import java.util.Optional;

public class AdminView extends ParentView {


    public AdminView(Controller controller, List<Product> productsList) {
        super(controller, productsList);
        controller.setAdminView(this);
        generateUserInterface();
    }

    private void createProduct() {
        Dialog<Product> dialog = new Dialog<>();
        dialog.setTitle("Add New Product");

        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, cancelButtonType);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField nameField = new TextField();
        nameField.setPromptText("Product Name");
        TextArea descriptionField = new TextArea();
        descriptionField.setPromptText("Product Description");
        TextField priceField = new TextField();
        priceField.setPromptText("Price");
        TextField quantityField = new TextField();
        quantityField.setPromptText("Quantity");

        grid.add(new Label("Name:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Description:"), 0, 1);
        grid.add(descriptionField, 1, 1);
        grid.add(new Label("Price:"), 0, 2);
        grid.add(priceField, 1, 2);
        grid.add(new Label("Quantity:"), 0, 3);
        grid.add(quantityField, 1, 3);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                try {
                    String name = nameField.getText();
                    String description = descriptionField.getText();
                    String priceText = priceField.getText();
                    String quantityText = quantityField.getText();

                    if (name.trim().isEmpty() || description.trim().isEmpty() || priceText.trim().isEmpty() || quantityText.trim().isEmpty()) {
                        showAlert(Alert.AlertType.ERROR, "All fields must be filled out.", "Please fill out all fields.");
                        return null;
                    }

                    double price = Double.parseDouble(priceText);
                    int quantity = Integer.parseInt(quantityText);
                    System.out.println(name + description + price + quantity);

                    return new Product(name, description, price, quantity);
                } catch (NumberFormatException e) {
                    showAlert(Alert.AlertType.ERROR, "Invalid number format.", "Please enter a valid discrete number for quantity.");
                    return null;
                }
            }
            return null;
        });

        Optional<Product> result = dialog.showAndWait();
        result.ifPresent(controller::saveProduct);
    }

    public void generateUserInterface() {
        TableView<Product> table = new TableView<>();
        table.setEditable(true);

        TableColumn<Product, String> nameColumn = new TableColumn<>("Product Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Product, String> descriptionColumn = new TableColumn<>("Product Description");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<Product, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Product, Integer> quantityColumn = new TableColumn<>("Quantity");
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("storedQuantity"));

        table.getColumns().addAll(nameColumn, descriptionColumn, priceColumn, quantityColumn);
        TableColumn<Product, Void> deleteColumn = new TableColumn<>("");

        deleteColumn.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Delete");

            {
                deleteButton.setOnAction(event -> {
                    Product product = getTableView().getItems().get(getIndex());
                    deleteProduct(product);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });

        table.getColumns().add(deleteColumn);

        table.setItems(products);
        quantityColumn.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<>() {
            @Override
            public String toString(Integer value) {
                return value.toString();
            }

            @Override
            public Integer fromString(String string) {
                return Integer.valueOf(string);
            }
        }));

        quantityColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<Product, Integer> t) -> {
                    products.get(t.getTablePosition().getRow()).setStoredQuantity(t.getNewValue());
                    controller.saveProduct(products.get(t.getTablePosition().getRow()));

                });

        Button addButton = new Button("Add Product");
        addButton.setOnAction(e -> createProduct());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(table, addButton);
        this.getChildren().addAll(layout);
    }

    // TODO: Implement deleteProduct() method
    private void deleteProduct(Product product) {
        controller.deleteProduct(product);
    }

}
