import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class UserLoginTest {

    public UserClient userClient;
    public String accessToken;
    public ExtractableResponse extractableResponseCreateUser;
    public ValidatableResponse responseCreateUser;
    public  User user;

    @Before
    public void setUp() {
        userClient = new UserClient();
        user = User.getRandom();
        responseCreateUser = userClient.create(user);
    }

    @After
    public void ternDown() {
        extractableResponseCreateUser = responseCreateUser.extract();
        accessToken = extractableResponseCreateUser.body().path("accessToken").toString().substring(7);
        userClient.delete(accessToken);
    }

    @Test
    @DisplayName("post auth login")
    @Description("Check login user success auth/login")
    public void postLoginUserDataLoginedUser() {
        ValidatableResponse responseLoginUser = userClient.login(UserCredentials.getUserCredentials(user));

        responseLoginUser.assertThat().body("success", is(true));
        responseLoginUser.assertThat().body("accessToken", notNullValue());
        responseLoginUser.assertThat().body("refreshToken", notNullValue());
        responseLoginUser.assertThat().body("user.email", is(user.email));
        responseLoginUser.assertThat().body("user.name", is(user.name));
    }

    @Test
    @DisplayName("post auth login password not correct")
    @Description("Check login user login and password fields are not correct auth/login")
    public void postLoginUserPasswordNotCorrectMessageError() {
        ValidatableResponse responseLoginUser = userClient.loginDataNotCorrect(UserCredentials.getUserCredentialsIsNotCorrect());

        responseLoginUser.assertThat().body("success", is(false));
        responseLoginUser.assertThat().body("message", is("email or password are incorrect"));
    }
}
