package models;

import exceptions.ConsumableTypeNotFound;
import exceptions.ProductNotFoundException;
import persistence.LoaderInventory;
import persistence.WriterInventory;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class Restaurant {
    private ArrayList<Dish> dishList;
    private ArrayList<Ingredient> ingredientList;
    private ArrayList<FoodPackage> foodPackagesList;
    private ArrayList<Sale> historialSales;

    public Restaurant() throws IOException {
        fillList();
        writeData();
    }

    public void random() {
        dishList.sort((Comparator) Comparator.comparing(o -> new Integer(((Dish) (o)).getId())));
    }

    private void fillList() throws IOException {
        dishList = LoaderInventory.loadDishes("data/DishesInventory.json");
        ingredientList = LoaderInventory.loadIngredients("data/IngredientsInventory.json");
        foodPackagesList = LoaderInventory.loadFoodPackage("data/FoodPackagesInventory.json");
        historialSales = LoaderInventory.loadSales("data/HistorialSales.json");
        if (dishList == null)
            dishList = new ArrayList<>();
        if (ingredientList == null)
            ingredientList = new ArrayList<>();
        if (foodPackagesList == null)
            foodPackagesList = new ArrayList<>();
        if (historialSales == null)
            historialSales = new ArrayList<>();
    }

    public ArrayList<Consumable> getListProbablyResults(String text) {
        ArrayList<Consumable> data = new ArrayList<>();
        for (Consumable consumable : getProducts_toSell())
            if (consumable.toString().toLowerCase().contains(text.toLowerCase()))
                data.add(consumable);

        data.sort((o1, o2) -> o1.getId().compareTo(String.valueOf(o2)));
        return data;
    }

    public ArrayList<Ingredient> getListProbablyResultsIngredients(String text) {
        ArrayList<Ingredient> data = new ArrayList<>();
        for (Ingredient consumable : ingredientList)
            if (consumable.toString().toLowerCase().contains(text.toLowerCase()))
                data.add(consumable);

        data.sort((o1, o2) -> o1.getId().compareTo(String.valueOf(o2)));
        return data;

    }

    public ArrayList<Consumable> getListProbablyResultsAllProducts(String text) {
        ArrayList<Consumable> data = new ArrayList<>();
        for (Consumable consumable : getAllProducts())
            if (consumable.toString().toLowerCase().contains(text.toLowerCase()))
                data.add(consumable);
        Set<Consumable> set = new HashSet<>(data);
        data.clear();
        data.addAll(set);
        data.sort((o1, o2) -> o1.getId().compareTo(String.valueOf(o2)));
        return data;
    }

    public ArrayList<Consumable> getAllProducts() {
        ArrayList<Consumable> dataFinal = new ArrayList<>();
        dataFinal.addAll(dishList);
        dataFinal.addAll(ingredientList);
        dataFinal.addAll(foodPackagesList);
        dataFinal.sort((o1, o2) -> o1.getId().compareTo(String.valueOf(o2)));

        return dataFinal;
    }

    private ArrayList<Consumable> getProducts_toSell() {
        ArrayList<Consumable> dataFinal = new ArrayList<>();
        dataFinal.addAll(dishList);
        dataFinal.addAll(foodPackagesList);
        dataFinal.sort((o1, o2) -> o1.getId().compareTo(String.valueOf(o2)));
        return dataFinal;
    }

    public Consumable searchProduct(String id) throws ProductNotFoundException {
        for (Consumable consumable : getProducts_toSell())
            if (consumable.getId().equals(id))
                return consumable;
        throw new ProductNotFoundException();
    }

    public Consumable searchProductAll(String id) throws ProductNotFoundException {
        for (Consumable consumable : getAllProducts())
            if (consumable.getId().equals(id))
                return consumable;
        throw new ProductNotFoundException();
    }

    public Ingredient searchIngredient(String id) throws ProductNotFoundException {
        for (Ingredient consumable : ingredientList)
            if (consumable.getId().equals(id))
                return consumable;
        throw new ProductNotFoundException();
    }

    public void writeData() {
        try {
            WriterInventory.writeIngredient(ingredientList);
            WriterInventory.writeDish(dishList);
            WriterInventory.writeFoodPackage(foodPackagesList);
            WriterInventory.writeHistorialSale(historialSales);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Consumable createConsumable(String id, String name, LocalDate expirationDate, double price, double quantity, ArrayList<Ingredient> ingredients) throws ConsumableTypeNotFound {
        if (id.toLowerCase().contains("i"))
            return new Ingredient(id, name, expirationDate, price, quantity);
        else if (id.toLowerCase().contains("p"))
            return new FoodPackage(price, id, name, expirationDate, quantity);
        else if (id.toLowerCase().contains("d"))
            return new Dish(id, name, expirationDate, ingredients);
        throw new ConsumableTypeNotFound();
    }

    public void editConsumable(Consumable consumable) throws ProductNotFoundException {
        Consumable consumableToEdit = searchProductAll(consumable.getId());
        consumableToEdit.setId(consumable.getId());
        consumableToEdit.setExpirationDate(consumable.getExpirationDate());
        consumableToEdit.setName(consumable.getName());
        consumableToEdit.setPrice(consumable.getPrice());
        consumableToEdit.setQuantity(consumable.getQuantity());
        if (consumable.getId().toLowerCase().contains("d")) {
            ((Dish) consumableToEdit).setIngredients(((Dish) consumable).getIngredients());
            consumableToEdit.setQuantity(1);
            consumableToEdit.setPrice(((Dish) consumable).calculatePrice());
        }

    }

    public String generateIdIngredient() {
        String newId = (ingredientList.size() + 1) + "I";
        int aux = 0;
        while (!verifyId(newId))
            newId = (ingredientList.size() + ++aux) + "I";
        return newId;

    }

    private boolean verifyId(String id) {
        for (int i = 0; i < getAllProducts().size(); i++) {
            if (getAllProducts().get(i).getId().equalsIgnoreCase(id))
                return false;
        }
        return true;
    }

    public String generateIdFoodPackage() {
        String newId = (foodPackagesList.size() + 1) + "P";
        int aux = 0;
        while (!verifyId(newId))
            newId = (foodPackagesList.size() + ++aux) + "P";
        return newId;
    }

    public String generateIdDish() {
        String newId = (dishList.size() + 1) + "D";
        int aux = 0;
        while (!verifyId(newId))
            newId = (dishList.size() + ++aux) + "D";
        return newId;
    }

    public void removeConsumable(String id) {
        for (int i = 0; i < dishList.size(); i++)
            if (dishList.get(i).getId().equalsIgnoreCase(id))
                dishList.remove(dishList.get(i));
        for (int i = 0; i < ingredientList.size(); i++)
            if (ingredientList.get(i).getId().equalsIgnoreCase(id))
                ingredientList.remove(ingredientList.get(i));
        for (int i = 0; i < foodPackagesList.size(); i++)
            if (foodPackagesList.get(i).getId().equalsIgnoreCase(id))
                foodPackagesList.remove(foodPackagesList.get(i));


    }

    public void addConsumable(Consumable consumable) {
        if (consumable.getId().toLowerCase().contains("i"))
            ingredientList.add((Ingredient) consumable);
        else if (consumable.getId().toLowerCase().contains("d"))
            dishList.add((Dish) consumable);
        else if (consumable.getId().toLowerCase().contains("p"))
            foodPackagesList.add((FoodPackage) consumable);
    }

    public void sellProducts(Client client, double totalPrice) {
        historialSales.add(new Sale(client, LocalDate.now(), totalPrice));
    }

    public static Client createClient(String document, ArrayList<Consumable> products) {
        return new Client(document, products);
    }

    public static Client createClient(String document, ArrayList<Consumable> products, String address) {
        return new Client(document, products, address, true);
    }

    public ArrayList<Sale> getHistorial() {
        return historialSales;
    }
}