package models;

import java.time.LocalDate;
import java.util.ArrayList;

public class Sale {
    private Client client;
    private LocalDate dateOfSale;
    private double totalPrice;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public LocalDate getDateOfSale() {
        return dateOfSale;
    }

    public void setDateOfSale(LocalDate dateOfSale) {
        this.dateOfSale = dateOfSale;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Sale(Client client, LocalDate dateOfSale, double totalPrice) {
        this.client = client;
        this.dateOfSale = dateOfSale;
        this.totalPrice = totalPrice;
    }

    public String[] toArray(){
        return new String[]{client.getDocument(),String.valueOf(totalPrice)};
    }
}
