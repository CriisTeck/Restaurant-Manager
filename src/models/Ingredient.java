package models;

import java.time.LocalDate;

public class Ingredient extends Consumable{


    public Ingredient(String id, String name, LocalDate expirationDate, double priceByKGram, double quantityByKGram) {
        super(id, name, expirationDate);
        super.setQuantity(quantityByKGram);
        super.setPrice(priceByKGram);
    }
}
