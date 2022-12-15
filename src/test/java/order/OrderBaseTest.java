package order;

import generator.CreateUserGenerator;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import pojo.CreateUser;
import user.UserClient;

public class OrderBaseTest {

    ValidatableResponse response;
    CreateUser request;
    String accessToken;

    @Before
    @DisplayName("Создание пользователя с валидными данными")
    @Description("Ожидание ответа 200")
    public void createUser() {
        request = CreateUserGenerator.getRandomNewUserGenerator();
        response = UserClient.createNewUser(request);
        accessToken = response.extract().path("accessToken");
    }

    @After
    @DisplayName("Удаление пользователя")
    public void tearDown() {
        if (accessToken != null) {
            UserClient.userDelete(accessToken);
        }
    }
}
