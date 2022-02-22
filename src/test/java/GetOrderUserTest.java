import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class GetOrderUserTest {

    public User user;
    public UserClient userClient;
    public OrderClient orderClient;
    public String accessToken;

    @Before
    public void setUp() {
        userClient = new UserClient();
        orderClient = new OrderClient();
    }

    @After
    public void ternDown() {
        userClient.delete(accessToken);
    }

    @Test
    @DisplayName("get orders for user")
    @Description("Check get orders for use /order")
    public void getOrderUserAuthUserOrder() {
        user = User.getRandom();
        ValidatableResponse responseCreateUser = userClient.create(user);
        ExtractableResponse extractableResponseCreateUser = responseCreateUser.extract();
        accessToken = extractableResponseCreateUser.body().path("accessToken").toString().substring(7);

        Order order = Order.getIngredients();
        orderClient.create(order, accessToken);

        ValidatableResponse responseOrders = orderClient.getOrderForUser(accessToken);

        responseOrders.assertThat().body("success", is(true));
        responseOrders.assertThat().body("total", notNullValue());
        responseOrders.assertThat().body("totalToday", notNullValue());
        responseOrders.assertThat().body("orders[0]._id", notNullValue());
        responseOrders.assertThat().body("orders[0].ingredients", notNullValue());
        responseOrders.assertThat().body("orders[0].createdAt", notNullValue());
        responseOrders.assertThat().body("orders[0].updatedAt", notNullValue());
        responseOrders.assertThat().body("orders[0].number", notNullValue());
        responseOrders.assertThat().body("orders[0].status", is("done"));
        responseOrders.assertThat().body("orders[0].name", is("Флюоресцентный бессмертный spicy бургер"));
    }

    @Test
    @DisplayName("get orders for user without auth")
    @Description("Check get orders for use without auth /order")
    public void getOrderWithoutAuthMessageError() {
        ValidatableResponse responseOrders = orderClient.getOrdersForUserWithoutAuth();

        responseOrders.assertThat().body("success", is(false));
        responseOrders.assertThat().body("message", is("You should be authorised"));
    }
}
