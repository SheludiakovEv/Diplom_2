package order;

import generator.CreateUserGenerator;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import pojo.CreateOrder;
import pojo.CreateUser;
import user.UserClient;

import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertTrue;

public class GetOrdersTest extends OrderBaseTest {
    private static final String ERROR_401 = "You should be authorised";
    ValidatableResponse response;
    ValidatableResponse order;
    CreateUser request;
    CreateOrder createOrder;

    @Test
    @DisplayName("Список заказов без авторизации")
    @Description("Ожидание ответа 401")
    public void listOfOrdersWithOutAuthorization() {
        order = OrderClient.receivingOrderWithOutAuthorization();
        order.assertThat()
                .statusCode(401)
                .and()
                .body("message", equalTo(ERROR_401));
    }

    @Test
    @DisplayName("Список заказов с авторизацией")
    @Description("Ожидание ответа 200")
    public void listOfOrdersWithAuthorization() {
        request = CreateUserGenerator.getRandomNewUserGenerator();
        response = UserClient.createNewUser(request);
        createOrder = new CreateOrder(OrderClient.getIngredients());
        order = OrderClient.creatingOrderWithLogin(createOrder, response.extract().path("accessToken").toString());
        String createOrderId = order.extract().path("order._id");
        order = OrderClient.receivingOrderWithAuthorization(response.extract().path("accessToken").toString());
        List<String> getOrderId = order.extract().path("orders._id");
        order.assertThat()
                .statusCode(200)
                .and()
                .body("success", equalTo(true));
        assertTrue("Списки заказов не совпадают", getOrderId.contains(createOrderId));
    }
}
