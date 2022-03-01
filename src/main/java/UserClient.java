import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.*;

public class UserClient extends RestAssuredClient {

    private String AUTH_PATH = "auth";

    @Step("Create user with error request")
    public ValidatableResponse createError(User user){
        return given()
                .spec(getBaseSpec())
                .body(user)
                .when()
                .post(AUTH_PATH + "/register")
                .then();
    }

    @Step("Create user request")
    public ValidatableResponse create(User user){
        return given()
                .spec(getBaseSpec())
                .body(user)
                .when()
                .post(AUTH_PATH + "/register")
                .then();
    }

    @Step("Login user request")
    public ValidatableResponse login(UserCredentials userCredentials){
        return given()
                .spec(getBaseSpec())
                .body(userCredentials)
                .when()
                .post(AUTH_PATH + "/login")
                .then();
    }

    @Step("Login data not correct request")
    public ValidatableResponse loginDataNotCorrect(UserCredentials userCredentials){
        return given()
                .spec(getBaseSpec())
                .body(userCredentials)
                .when()
                .post(AUTH_PATH + "/login")
                .then();
    }

    @Step("Changing user data request")
    public ValidatableResponse changingUserData(UserChanging userChanging, String auth){
        return given()
                .spec(getBaseSpec())
                .auth().oauth2(auth)
                .body(userChanging)
                .when()
                .patch(AUTH_PATH + "/user")
                .then();
    }

    @Step("Changing user data error request")
    public ValidatableResponse changingUserDataError(UserChanging userChanging){
        return given()
                .spec(getBaseSpec())
                .body(userChanging)
                .when()
                .patch(AUTH_PATH + "/user")
                .then();
    }

    @Step("Delete user")
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
