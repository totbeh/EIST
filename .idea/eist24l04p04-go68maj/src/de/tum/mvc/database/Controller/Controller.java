package de.tum.mvc.database.Controller;

import de.tum.mvc.database.Model.DatabaseHelper;
import de.tum.mvc.database.Model.Product;
import de.tum.mvc.database.Model.ProductDAO;
import de.tum.mvc.database.View.AdminView;
import de.tum.mvc.database.View.CustomerView;

import java.util.List;

public class Controller {
    private final ProductDAO productDAO;

    private CustomerView customerView;
    private AdminView adminView;

    public Controller(DatabaseHelper databaseHelper) {
        this.productDAO = new ProductDAO(databaseHelper);
    }

    public void setAdminView(AdminView adminView) {
        this.adminView = adminView;
    }

    public void setCustomerView(CustomerView customerView) {
        this.customerView = customerView;
    }

        public void saveProduct(Product product) {
            if (product.getId() == -1) {
                int newId = productDAO.saveProduct(product);
                Product newProduct = new Product(newId, product.getName(), product.getDescription(), product.getPrice(), product.getStoredQuantity());
                adminView.addProduct(newProduct);
                customerView.addProduct(newProduct);
                newProduct.notifyObservers();
            } else {
                // Check if the product already exists in the database
                Product oldProduct = productDAO.selectProductById(product.getId());
                if (oldProduct != null) {
                    productDAO.updateProduct(product);

                } else {

                    int newId = productDAO.saveProduct(product);
                    Product newProduct = new Product(newId, product.getName(), product.getDescription(), product.getPrice(), product.getStoredQuantity());

                }
                if (adminView != null) {
                    adminView.addProduct(product);
                }
                if (customerView != null) {
                    customerView.addProduct(product);
                }


            }
            product.notifyObservers();
        }


    public List<Product> getProducts() {
        return productDAO.selectProducts();
    }

    // TODO: Implement this buyProduct() method
    public void buyProduct(Product product, int quantity) throws Exception {
        if (product.getStoredQuantity() >= quantity){
            product.setStoredQuantity(product.getStoredQuantity()-quantity);
            productDAO.updateProduct(product);
            product.notifyObservers();
            return;
        }
        throw new Exception("Insufficient stock. Purchase not successful. "+product.getStoredQuantity()+" left.");
    }

    public void deleteProduct(Product product) {
        boolean deleted = productDAO.deleteProduct(product);
        if (deleted) {
            product.notifyObservers();
            System.out.println("Product deleted successfully: " + product.getName());
        } else {
            System.out.println("Failed to delete product: " + product.getName());
        }

    }
}
