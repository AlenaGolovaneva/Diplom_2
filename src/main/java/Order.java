import java.util.ArrayList;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.*;
import static org.apache.http.HttpStatus.*;

public class Order {
    public final ArrayList<String> ingredients;

    public Order(ArrayList<String> ingredients){
        this.ingredients = ingredients;
    }

    public static Order getIngredients(){
        ArrayList<String> ingredients = new ArrayList<>();
        //Флюоресцентная булка R2-D3
        ingredients.add("61c0c5a71d1f82001bdaaa6d");
        //Мясо бессмертных моллюсков Protostomia
        ingredients.add("61c0c5a71d1f82001bdaaa6f");
        //Соус Spicy-X
        ingredients.add("61c0c5a71d1f82001bdaaa72");
        return new Order(ingredients);
    }

    public static Order getEmptyIngredients(){
        ArrayList<String> ingredients = new ArrayList<>();
        return new Order(ingredients);
    }

    public static Order getInstanceHashIsNotCorrect(){
        ArrayList<String> ingredients = new ArrayList<>();
        ingredients.add("61c0cwwwwwwwwwa6dwww");
        ingredients.add("6wwwwwwf");
        return new Order(ingredients);
    }

}
