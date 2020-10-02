package persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import models.Dish;
import models.FoodPackage;
import models.Ingredient;
import models.Sale;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

public class WriterInventory {

    public static void writeIngredient(ArrayList<Ingredient> list) throws IOException {
        Writer writer = new FileWriter("data/IngredientsInventory.json");
        createGsonPrettyPrintable().toJson(list, writer);
        writer.close();
    }

    public static void writeDish(ArrayList<Dish> list) throws IOException {
        Writer writer = new FileWriter("data/DishesInventory.json");
        createGsonPrettyPrintable().toJson(list, writer);
        writer.close();
    }

    public static void writeFoodPackage(ArrayList<FoodPackage> list) throws IOException {
        Writer writer = new FileWriter("data/FoodPackagesInventory.json");
        createGsonPrettyPrintable().toJson(list, writer);
        writer.close();
    }

    private static Gson createGsonPrettyPrintable() {
        return new GsonBuilder().setPrettyPrinting().create();
    }

    public static void writeHistorialSale(ArrayList<Sale> historialSales) throws IOException {
        Writer writer = new FileWriter("data/HistorialSales.json");
        createGsonPrettyPrintable().toJson(historialSales, writer);
        writer.close();
    }
}
