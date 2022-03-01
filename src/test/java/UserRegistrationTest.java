import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class UserRegistrationTest {
    public UserClient userClient;
    public String accessToken;

    @Before
    public void setUp() {
        userClient = new UserClient();
    }

    @After
    public void ternDown() {
        userClient.delete(accessToken);
    }

    @Test
    @DisplayName("post auth register")
    @Description("Check register user success auth/register")
    public void postRegisterUserDataCreatedUser() {
        User user = User.getRandom();
        ValidatableResponse responseCreateUser = userClient.create(user);

        ExtractableResponse extractableResponseCreateUser = responseCreateUser.extract();
        accessToken = extractableResponseCreateUser.body().path("accessToken").toString().substring(7);

        responseCreateUser.assertThat().statusCode(SC_OK);
        responseCreateUser.assertThat().body("success", is(true));
        responseCreateUser.assertThat().body("accessToken", notNullValue());
        responseCreateUser.assertThat().body("refreshToken", notNullValue());
        responseCreateUser.assertThat().body("user.email", is(user.email));
        responseCreateUser.assertThat().body("user.name", is(user.name));
    }

    @Test
    @DisplayName("post auth register duplicate")
    @Description("Check register user duplicate error should be auth/register")
    public void postRegisterUserDuplicateMessageError() {
        User user = User.getRandom();
        ValidatableResponse responseSuccess = userClient.create(user);
        ValidatableResponse responseDuplicate = userClient.createError(user);

        ExtractableResponse extractableResponseCreateUser = responseSuccess.extract();
        accessToken = extractableResponseCreateUser.body().path("accessToken").toString().substring(7);

        responseDuplicate.assertThat().statusCode(SC_FORBIDDEN);
        responseDuplicate.assertThat().body("success", is(false));
        responseDuplicate.assertThat().body("message", is("User already exists"));
    }

    @Test
    @DisplayName("post auth register empty password")
    @Description("Check register user empty password error should be auth/register")
    public void postRegisterUserEmptyPasswordMessageError() {
        User user = User.getEmptyPassword();
        ValidatableResponse responseError = userClient.createError(user);

        responseError.assertThat().statusCode(SC_FORBIDDEN);
        responseError.assertThat().body("success", is(false));
        responseError.assertThat().body("message", is("Email, password and name are required fields"));
    }
}
