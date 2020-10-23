package models;

import java.time.LocalDate;

public class Sale {
    private Client client;
    @SuppressWarnings("FieldCanBeLocal")
    private final LocalDate dateOfSale;
    private final double totalPrice;

    public void setClient(Client client) {
        this.client = client;
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
