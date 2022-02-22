import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.*;

public class OrderClient extends RestAssuredClient{

    private String AUTH_PATH = "orders";
    @Step
    public ValidatableResponse create(Order order, String auth){
        return given()
                .spec(getBaseSpec())
                .auth().oauth2(auth)
                .body(order)
                .when()
                .post(AUTH_PATH)
                .then()
                .assertThat()
                .statusCode(SC_OK);
    }

    @Step
    public ValidatableResponse createWithoutAuth(Order order){
        return given()
                .spec(getBaseSpec())
                .body(order)
                .when()
                .post(AUTH_PATH)
                .then()
                .assertThat()
                .statusCode(SC_OK);
    }

    @Step
    public ValidatableResponse createHashIsNotCorrect(Order order, String auth){
        return given()
                .spec(getBaseSpec())
                .auth().oauth2(auth)
                .body(order)
                .when()
                .post(AUTH_PATH)
                .then()
                .assertThat()
                .statusCode(SC_INTERNAL_SERVER_ERROR)
                ;
    }

    @Step
    public ValidatableResponse createWithoutIngredients(Order order,String auth){
        return given()
                .spec(getBaseSpec())
                .auth().oauth2(auth)
                .body(order)
                .when()
                .post(AUTH_PATH)
                .then()
                .assertThat()
                .statusCode(SC_BAD_REQUEST);
    }

    @Step
    public ValidatableResponse getOrderForUser(String auth){
        return given()
                .spec(getBaseSpec())
                .auth().oauth2(auth)
                .when()
                .get(AUTH_PATH)
                .then()
                .log().all()
                .assertThat()
                .statusCode(SC_OK);
    }

    @Step
    public ValidatableResponse getOrdersForUserWithoutAuth(){
        return given()
                .spec(getBaseSpec())
                .when()
                .get(AUTH_PATH)
                .then()
                .log().all()
                .assertThat()
                .statusCode(SC_UNAUTHORIZED);
    }
}
