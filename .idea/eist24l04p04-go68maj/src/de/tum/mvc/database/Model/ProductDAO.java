package de.tum.mvc.database.Model;

import java.util.List;

public class ProductDAO {

    private final DatabaseHelper databaseHelper;

    public ProductDAO(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    // TODO: Implement saveProduct
    public int saveProduct(Product product) {
        return databaseHelper.insertProduct(product);
    }

    // TODO: Implement updateProduct
    public Product updateProduct(Product product) {
        return databaseHelper.updateProduct(product);
    }

    // TODO: Implement selectProducts
    public List<Product> selectProducts() {
        return databaseHelper.selectAllProducts();
    }

    // TODO: Implement deleteProduct
    public boolean deleteProduct(Product product) {
        return databaseHelper.deleteProduct(product);
    }

    public Product selectProductById(int id) {
        Product product = databaseHelper.selectProductById(id);

        if (product == null) {
            System.out.println("Couldn't find product with id: " + id);
        }

        return product;
    }


}
