package persistence;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import models.Dish;
import models.FoodPackage;
import models.Ingredient;
import models.Sale;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class LoaderInventory {

    private static String getStringJSON(String URL) throws IOException {
        StringBuilder jsonString = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(URL));
        String line;
        while ((line = reader.readLine()) != null) {
            jsonString.append(line);
        }
        return jsonString.toString();
    }

    public static ArrayList<Dish> loadDishes(String URL) throws IOException {
        Gson gson = createGSON();
        String jsonString = getStringJSON(URL);
        Type typeArray = new TypeToken<ArrayList<Dish>>() {
        }.getType();
        return gson.fromJson(jsonString, typeArray);
    }

    /**
     * Metodo que crea un objeto Gson, pero que recibe un string del formato "d/MM/yyyy" en el archivo json
     * y este se encarga de convertir ese String a un Objeto de tipo LocalDate.
     * @return Retorna instancia del objeto Gson con
     */
    private static Gson createGSONWithLocalDate() {
        return new GsonBuilder().registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>)
                (json, typeOfT, context) -> LocalDate.parse(json.getAsString(), DateTimeFormatter.ofPattern("d/MM/yyyy"))).create();
    }

    private static Gson createGSON() {
        return new Gson();
    }

    public static ArrayList<FoodPackage> loadFoodPackage(String URL) throws IOException {
        Gson gson = createGSON();
        String jsonString = getStringJSON(URL);
        Type typeArray = new TypeToken<ArrayList<FoodPackage>>() {
        }.getType();
        return gson.fromJson(jsonString, typeArray);
    }

    public static ArrayList<Ingredient> loadIngredients(String URL) throws IOException {
        Gson gson = createGSON();
        String jsonString = getStringJSON(URL);
        Type typeArray = new TypeToken<ArrayList<Ingredient>>() {
        }.getType();
        return gson.fromJson(jsonString, typeArray);
    }

    public static ArrayList<Sale> loadSales(String URL) throws IOException {
        Gson gson = createGSON();
        String jsonString = getStringJSON(URL);
        Type typeArray = new TypeToken<ArrayList<Sale>>() {
        }.getType();
        return gson.fromJson(jsonString, typeArray);
    }
}