package user;

import generator.CreateUserGenerator;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import pojo.CreateUser;

import static org.hamcrest.core.IsEqual.equalTo;

@DisplayName("Создание пользователя")
public class UserCreatingTest extends UserBaseTest {

    private static final String REGISTER_ERROR_403 = "Email, password and name are required fields";
    ValidatableResponse response;
    CreateUser request;

    @Test
    @DisplayName("Создание пользователя с валидными данными")
    @Description("Ожидание ответа 200")
    public void createUserAllCorrectParameters() {
        request = CreateUserGenerator.getRandomNewUserGenerator();
        response = UserClient.createNewUser(request);
        accessToken = response.extract().path("accessToken");
        response.assertThat().body("success", equalTo(true));
    }

    @Test
    @DisplayName("Создание пользователя с использованными данными")
    @Description("Ожидание ответа 403")
    public void createTwoIdenticalUser() {
        request = CreateUserGenerator.getRandomNewUserGenerator();
        response = UserClient.createNewUser(request);

        response.statusCode(200)
                .and()
                .assertThat().body("success", equalTo(true));

        ValidatableResponse errorResponse = UserClient.createNewUser(request);
        errorResponse.statusCode(403)
                .and()
                .assertThat()
                .body("message", equalTo("User already exists"));
    }

    @Test
    @DisplayName("Создание пользователя не заполнив поле Password")
    @Description("Ожидание ответа 403")
    public void createUserNotFilledPassword() {
        request = CreateUserGenerator.getRandomNotFilledPassword();
        response = UserClient.createNewUser(request);

        response.statusCode(403)
                .and()
                .assertThat()
                .body("message", equalTo(REGISTER_ERROR_403));
    }

    @Test
    @DisplayName("Создание пользователя не заполнив поле Name")
    @Description("Ожидание ответа 403")
    public void createUserNotFilledName() {
        request = CreateUserGenerator.getRandomNotFilledName();
        response = UserClient.createNewUser(request);

        response.statusCode(403)
                .and()
                .assertThat()
                .body("message", equalTo(REGISTER_ERROR_403));
    }

    @Test
    @DisplayName("Создание пользователя не заполнив поле Email")
    @Description("Ожидание ответа 403")
    public void createUserNotFilledEmail() {
        request = CreateUserGenerator.getRandomNotFilledEmail();
        response = UserClient.createNewUser(request);

        response.statusCode(403)
                .and()
                .assertThat()
                .body("message", equalTo(REGISTER_ERROR_403));
    }
}
