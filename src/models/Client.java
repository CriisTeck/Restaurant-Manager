package models;

import java.util.ArrayList;

public class Client {
    private String document;
    private ArrayList<Consumable> products;
    private String address;
    private boolean isDelivery;

    public Client(String document) {
        this.document = document;
        this.isDelivery = false;
    }

    public Client(String document, ArrayList<Consumable>products, String address, boolean isDelivery) {
        this.document = document;
        this.products = products;
        this.address = address;
        this.isDelivery = isDelivery;
    }

    public Client(String document, ArrayList<Consumable> products){
        this.document = document;
        this.isDelivery = false;
        this.products = products;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public ArrayList<Consumable> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Consumable> products) {
        this.products = products;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isDelivery() {
        return isDelivery;
    }

    public void setDelivery(boolean delivery) {
        isDelivery = delivery;
    }
}