package order;

import generator.CreateUserGenerator;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import pojo.CreateOrder;
import pojo.CreateUser;
import user.UserClient;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.core.IsEqual.equalTo;


public class CreateOrdersTest {

    private static final String ERROR_400 = "Ingredient ids must be provided";
    ValidatableResponse response;
    ValidatableResponse order;
    CreateOrder createOrder;
    CreateUser request;

    @Before
    @DisplayName("Создание пользователя с валидными данными")
    @Description("Ожидание ответа 200")
    public void createUser() {
        request = CreateUserGenerator.getRandomNewUserGenerator();
        response = UserClient.createNewUser(request);
    }

    @Test
    @DisplayName("Создание заказа с логином и ингредиентами")
    @Description("Ожидание ответа 200")
    public void creatingOrderWithAuthorizationAndIngredients() {
        List<String> ingredientsRequest = OrderClient.getIngredients();
        createOrder = new CreateOrder(ingredientsRequest);
        order = OrderClient.creatingOrderWithLogin(createOrder, response.extract().path("accessToken").toString());
        List<String> ingredientsResponse = order.extract().path("order.ingredients._id");
        order.assertThat()
                .statusCode(200)
                .and()
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Создание заказа без логина и ингредиентами")
    @Description("Ожидание ответа 200")
    public void creatingOrderWithOutAuthorizationAndIngredients() {
        List<String> ingredientsRequest = OrderClient.getIngredients();
        createOrder = new CreateOrder(ingredientsRequest);
        order = OrderClient.creatingOrderWithOutLogin(createOrder);
        List<String> ingredientsResponse = order.extract().path("order.ingredients._id");
        order.assertThat()
                .statusCode(200)
                .and()
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Создание заказа без логина и без ингредиентов")
    @Description("Ожидание ответа 400")
    public void createOrderWithOutAuthorizationButNoIngredients() {
        createOrder = new CreateOrder(null);
        order = OrderClient.creatingOrderWithOutLogin(createOrder);

        order.assertThat()
                .statusCode(400)
                .and()
                .body("message", equalTo(ERROR_400));
    }

    @Test
    @DisplayName("Создание заказа с логином, но без ингредиентов")
    @Description("Ожидание ответа 400")
    public void createOrderWithAuthorizationButNoIngredients() {
        createOrder = new CreateOrder(null);
        order = OrderClient.creatingOrderWithLogin(createOrder, response.extract().path("accessToken").toString());
        order.assertThat()
                .statusCode(400)
                .and()
                .body("message", equalTo(ERROR_400));
    }

    @Test
    @DisplayName("Создание заказа с логином и с неверными ингредиентами")
    @Description("Ожидание ответа 500")
    public void creatingOrderWithAuthorizationAndWithOutIngredients() {
        createOrder = new CreateOrder(Arrays.asList("noValid1", "noValid2"));
        order = OrderClient.creatingOrderWithLogin(createOrder, response.extract().path("accessToken").toString());
        order.assertThat()
                .statusCode(500);
    }

    @Test
    @DisplayName("Создание заказа без логина и с неверными ингредиентами")
    @Description("Ожидание ответа 500")
    public void creatingOrderWithOutAuthorizationAndWithOutIngredients() {
        createOrder = new CreateOrder(Arrays.asList(RandomStringUtils.randomAlphanumeric(10), RandomStringUtils.randomAlphanumeric(10)));
        order = OrderClient.creatingOrderWithOutLogin(createOrder);
        order.assertThat()
                .statusCode(500);
    }
}
