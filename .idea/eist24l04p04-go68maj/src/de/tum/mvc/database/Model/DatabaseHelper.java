package de.tum.mvc.database.Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {

    private final String SQLITE_CONNECTION_STRING;

    private final String PRODUCT_TABLE = "products";

    private final Connection conn;

    public DatabaseHelper(String databasePath) {
        this.SQLITE_CONNECTION_STRING = databasePath;
        conn = connect();
    }

    public synchronized Connection connect() {
        try {
            return DriverManager.getConnection(SQLITE_CONNECTION_STRING);
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database: " + e);
        }
    }

    public synchronized void initializeDatabase() {
        try (Statement statement = conn.createStatement()) {

            String createProducts = "CREATE TABLE IF NOT EXISTS " + PRODUCT_TABLE + " (\n"
                    + "	id integer PRIMARY KEY,\n"
                    + "	name text NOT NULL,\n"
                    + "	description text NOT NULL,\n"
                    + "	price real,\n"
                    + "	stored integer\n"
                    + ");";

            statement.addBatch(createProducts);

            statement.executeBatch();

        } catch (SQLException e) {
            throw new RuntimeException("Error initializing the database", e);
        }
    }

    public synchronized void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            System.err.println("Error closing the database connection: " + e.getMessage());
        }
    }

    public synchronized int insertProduct(Product product) {
        String sql = "INSERT INTO " + PRODUCT_TABLE + "(name,description,price,stored) VALUES(?,?,?,?)";

        try (
                PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setDouble(3, product.getPrice());
            preparedStatement.setInt(4, product.getStoredQuantity());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                return -1;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return -1;
        }
    }

    // TODO: Implement updateProduct() method
    public synchronized Product updateProduct(Product product) {
        String sql = "UPDATE " + PRODUCT_TABLE + " SET"
                + " name = ? , "
                + " description = ? , "
                + " price = ? , "
                + " stored = ? "
                + "WHERE id = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)){
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setDouble(3, product.getPrice());
            preparedStatement.setInt(4, product.getStoredQuantity());
            preparedStatement.setInt(5, product.getId());

           if (preparedStatement.executeUpdate() == 1){
               return selectProductById(product.getId());
           }else System.err.println("Something went wrong updating product with id "+ product.getId());
           return null;


        } catch (SQLException e) {
            System.err.println("Something went wrong updating product with id "+ product.getId());
            return null;
        }



    }


    public synchronized Product selectProductById(int id) {
        String sql = "SELECT * FROM " + PRODUCT_TABLE + " WHERE id = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Product> products = parseResultToProduct(resultSet);

            return products.size() > 0 ? products.get(0) : null;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public List<Product> selectAllProducts() {
        String sql = "SELECT * FROM " + PRODUCT_TABLE;

        try {
            Statement statement = conn.createStatement();
            return parseResultToProduct(statement.executeQuery(sql));
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }


    public boolean deleteProduct(Product product) {
        String sql = "DELETE FROM " + PRODUCT_TABLE + " WHERE id = ?";

        try (
                PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, product.getId());

            int rows = preparedStatement.executeUpdate();

            return rows == 1;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public List<Product> parseResultToProduct(ResultSet resultSet) {
        List<Product> products = new ArrayList<>();
        try {
            while (resultSet.next()) {
                products.add(new Product(resultSet.getInt("id"), resultSet.getString("name"),
                        resultSet.getString("description"), resultSet.getDouble("price"), resultSet.getInt("stored")));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return List.of();
        }

        return products;
    }

}