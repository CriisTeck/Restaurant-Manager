package models;

import java.time.LocalDate;

public class Consumable {
    private String id;
    private String name;
    private LocalDate expirationDate;
    private double price;
    private double quantity;

    public Consumable(String id, String name, LocalDate expirationDate) {
        this.id = id;
        this.name = name;
        this.expirationDate = expirationDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String toString() {
        return id + " - " + name;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity){
        this.quantity = quantity;
    }
}
