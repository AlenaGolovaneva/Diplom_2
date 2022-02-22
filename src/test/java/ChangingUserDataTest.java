import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

public class ChangingUserDataTest {
    public User user;
    public UserClient userClient;
    public UserChanging uerChanging;
    public String accessToken;

    @Before
    public void setUp() {
        userClient = new UserClient();
        uerChanging = UserChanging.getChangingUserData();
    }

    @After
    public void ternDown() {
        userClient.delete(accessToken);
    }

    @Test
    @DisplayName("patch auth user with authorization")
    @Description("Check changing date with login user success auth/user")
    public void patchUserWithAuthChangingUserData() {
        user = User.getRandom();
        ValidatableResponse responseCreateUser = userClient.create(user);

        ExtractableResponse extractableResponseCreateUser = responseCreateUser.extract();
        accessToken = extractableResponseCreateUser.body().path("accessToken").toString().substring(7);

        ValidatableResponse responseChangingUser = userClient.changingUserData(uerChanging, accessToken);

        responseChangingUser.assertThat().body("success", is(true));
        responseChangingUser.assertThat().body("user.email", is(uerChanging.email));
        responseChangingUser.assertThat().body("user.name", is(uerChanging.name));
    }

    @Test
    @DisplayName("patch auth user without authorization")
    @Description("Check changing date without authorization auth/user")
    public void patchUserWithoutAutMessageError() {
        ValidatableResponse responseChangingUser = userClient.changingUserDataError(uerChanging);

        responseChangingUser.assertThat().body("success", is(false));
        responseChangingUser.assertThat().body("message", is("You should be authorised"));
    }

}
