package de.tum.mvc.database.Model;

// create product that is stored in sqlite database
public class Product extends Observable {
    private int id;
    private String name;
    private String description;
    private double price;
    private int storedQuantity;

    public Product(int id, String name, String description, double price, int storedQuantity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.storedQuantity = storedQuantity;
    }

    public Product(String name, String description, double price, int storedQuantity) {
        this.id = -1;
        this.name = name;
        this.description = description;
        this.price = price;
        this.storedQuantity = storedQuantity;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public int getStoredQuantity() {
        return storedQuantity;
    }

    public void setStoredQuantity(int storedQuantity) {
        this.storedQuantity = storedQuantity;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", storedQuantity=" + storedQuantity +
                '}';
    }
}