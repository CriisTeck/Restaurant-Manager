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
import java.util.HashMap;

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

    /**
     * Load Dishes data from json file.
     * @param URL String with the json path file.
     * @return {@link ArrayList} with the dishes json data.
     * @throws IOException Throws a Exception.
     */
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
     * @return Gson object with Localdate format.
     */
    private static Gson createGSONWithLocalDate() {
        return new GsonBuilder().registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>)
                (json, typeOfT, context) -> LocalDate.parse(json.getAsString(), DateTimeFormatter.ofPattern("d/MM/yyyy"))).create();
    }

    /**
     * Create a Gson Object.
     * @return Instance of {@link Gson}
     */
    private static Gson createGSON() {
        return new Gson();
    }

    /**
     * Load food-packages data from json file.
     * @param URL String with the json path file.
     * @return List of type:  {@link java.util.ArrayList} with de json data.
     * @throws IOException Throws IOException.
     */
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