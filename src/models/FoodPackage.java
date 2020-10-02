package models;

import java.time.LocalDate;

public class FoodPackage extends Consumable {

    public FoodPackage(double price,String id, String name, LocalDate expirationDate, double quantity) {
        super(id, name, expirationDate);
        super.setQuantity(quantity);
        super.setPrice(price);
    }

}
