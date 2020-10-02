package models;

import java.time.LocalDate;
import java.util.ArrayList;

public class Dish extends Consumable{
    private ArrayList<Ingredient> ingredients;

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public Dish(String id, String name, LocalDate expirationDate, ArrayList<Ingredient> ingredients) {
        super(id, name, expirationDate);
        this.ingredients =ingredients;
        super.setPrice(calculatePrice());
        super.setQuantity(1);
    }

    public Dish(String id, String name, LocalDate expirationDate, ArrayList<Ingredient> ingredients, double price) {
        super(id, name, expirationDate);
        this.ingredients =ingredients;
        super.setPrice(price);
        super.setQuantity(1);
    }

    public int calculatePrice(){
        int priceTotal = 0;
        for (Consumable consumable : ingredients)
            priceTotal += consumable.getPrice();
        return priceTotal;
    }

}
