import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderClient extends RestAssuredClient{

    private String AUTH_PATH = "orders";
    @Step("Create order request")
    public ValidatableResponse create(Order order, String auth){
        return given()
                .spec(getBaseSpec())
                .auth().oauth2(auth)
                .body(order)
                .when()
                .post(AUTH_PATH)
                .then();
    }

    @Step("Create order without auth request")
    public ValidatableResponse createWithoutAuth(Order order){
        return given()
                .spec(getBaseSpec())
                .body(order)
                .when()
                .post(AUTH_PATH)
                .then();
    }

    @Step("Create order hash is not correct request")
    public ValidatableResponse createHashIsNotCorrect(Order order, String auth){
        return given()
                .spec(getBaseSpec())
                .auth().oauth2(auth)
                .body(order)
                .when()
                .post(AUTH_PATH)
                .then();
    }

    @Step("Create order without ingredients request")
    public ValidatableResponse createWithoutIngredients(Order order,String auth){
        return given()
                .spec(getBaseSpec())
                .auth().oauth2(auth)
                .body(order)
                .when()
                .post(AUTH_PATH)
                .then();
    }

    @Step("Get order for user request")
    public ValidatableResponse getOrderForUser(String auth){
        return given()
                .spec(getBaseSpec())
                .auth().oauth2(auth)
                .when()
                .get(AUTH_PATH)
                .then()
                .log().all();
    }

    @Step("Get orders for user without auth ")
    public ValidatableResponse getOrdersForUserWithoutAuth(){
        return given()
                .spec(getBaseSpec())
                .when()
                .get(AUTH_PATH)
                .then()
                .log().all();
    }
}
