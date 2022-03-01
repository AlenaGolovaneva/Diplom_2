import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.*;

public class CreatingOrderTest {

    public User user;
    public UserClient userClient;
    public OrderClient orderClient;
    public String accessToken;

    @Before
    public void setUp(){
        userClient = new UserClient();
        orderClient = new OrderClient();
    }

    @After
    public void ternDown() {
        userClient.delete(accessToken);
    }

    @Test
    @DisplayName("post order")
    @Description("Check create order /order")
    public void postOrderDataOrderCreatedOrder() {
        user = User.getRandom();
        ValidatableResponse responseCreateUser = userClient.create(user);
        ExtractableResponse extractableResponseCreateUser = responseCreateUser.extract();
        accessToken = extractableResponseCreateUser.body().path("accessToken").toString().substring(7);

        Order order = Order.getIngredients();
        ValidatableResponse responseOrder = orderClient.create(order, accessToken);

        responseOrder.assertThat().statusCode(SC_OK);
        responseOrder.assertThat().body("success", is(true));
        responseOrder.assertThat().body("order.number", notNullValue());
        responseOrder.assertThat().body("order.name", is("Флюоресцентный бессмертный spicy бургер"));
        responseOrder.assertThat().body("order.owner.name", is(user.name));
        responseOrder.assertThat().body("order.owner.email", is(user.email));
        responseOrder.assertThat().body("name", is("Флюоресцентный бессмертный spicy бургер"));
    }

    @Test
    @DisplayName("post order without auth")
    @Description("Check create order without auth /order")
    public void postOrderWithoutAutCreatedOrder() {
        Order order = Order.getIngredients();
        ValidatableResponse responseOrder = orderClient.createWithoutAuth(order);

        responseOrder.assertThat().statusCode(SC_OK);
        responseOrder.assertThat().body("success", is(true));
        responseOrder.assertThat().body("order.number", notNullValue());
        responseOrder.assertThat().body("name", is("Флюоресцентный бессмертный spicy бургер"));
    }

    @Test
    @DisplayName("post order hash is not correct")
    @Description("Check create order hash is not correct /order")
    public void postOrderHashIsNotCorrectInternalServerError() {
        user = User.getRandom();
        ValidatableResponse responseCreateUser = userClient.create(user);
        ExtractableResponse extractableResponseCreateUser = responseCreateUser.extract();
        accessToken = extractableResponseCreateUser.body().path("accessToken").toString().substring(7);

        Order order = Order.getInstanceHashIsNotCorrect();
        ValidatableResponse responseOrder = orderClient.createHashIsNotCorrect(order, accessToken);

        responseOrder.assertThat().statusCode(SC_INTERNAL_SERVER_ERROR);
        responseOrder.assertThat().body(containsString("Internal Server Error"));
    }

    @Test
    @DisplayName("post order without ingredients")
    @Description("Check create order without ingredients /order")
    public void postOrderWithOutIngredientsMessageError() {
        user = User.getRandom();
        ValidatableResponse responseCreateUser = userClient.create(user);
        ExtractableResponse extractableResponseCreateUser = responseCreateUser.extract();
        accessToken = extractableResponseCreateUser.body().path("accessToken").toString().substring(7);

        Order order = Order.getEmptyIngredients();
        ValidatableResponse responseOrder = orderClient.createWithoutIngredients(order, accessToken);

        responseOrder.assertThat().statusCode(SC_BAD_REQUEST);
        responseOrder.assertThat().body("success", is(false));
        responseOrder.assertThat().body("message", is("Ingredient ids must be provided"));
    }
}
