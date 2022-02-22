import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.*;
import static org.apache.http.HttpStatus.*;

public class UserClient extends RestAssuredClient {

    private String AUTH_PATH = "auth";

    @Step
    public ValidatableResponse createError(User user){
        return given()
                .spec(getBaseSpec())
                .body(user)
                .when()
                .post(AUTH_PATH + "/register")
                .then()
                .assertThat()
                .statusCode(SC_FORBIDDEN);
    }

    @Step
    public ValidatableResponse create(User user){
        return given()
                .spec(getBaseSpec())
                .body(user)
                .when()
                .post(AUTH_PATH + "/register")
                .then()
                .assertThat()
                .statusCode(SC_OK);
    }

    @Step
    public ValidatableResponse login(UserCredentials userCredentials){
        return given()
                .spec(getBaseSpec())
                .body(userCredentials)
                .when()
                .post(AUTH_PATH + "/login")
                .then()
                .assertThat()
                .statusCode(SC_OK);
    }

    @Step
    public ValidatableResponse loginDataNotCorrect(UserCredentials userCredentials){
        return given()
                .spec(getBaseSpec())
                .body(userCredentials)
                .when()
                .post(AUTH_PATH + "/login")
                .then()
                .assertThat()
                .statusCode(SC_UNAUTHORIZED);
    }

    @Step
    public ValidatableResponse changingUserData(UserChanging userChanging, String auth){
        return given()
                .spec(getBaseSpec())
                .auth().oauth2(auth)
                .body(userChanging)
                .when()
                .patch(AUTH_PATH + "/user")
                .then()
                .assertThat()
                .statusCode(SC_OK);
    }

    @Step
    public ValidatableResponse changingUserDataError(UserChanging userChanging){
        return given()
                .spec(getBaseSpec())
                .body(userChanging)
                .when()
                .patch(AUTH_PATH + "/user")
                .then()
                .assertThat()
                .statusCode(SC_UNAUTHORIZED);
    }


    @Step
    public void delete(String token){
        if(token != null)
        {
            given()
                    .spec(getBaseSpec())
                    .auth().oauth2(token)
                    .when()
                    .delete("auth/user");
        }
    }



}
