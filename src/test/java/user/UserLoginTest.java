package user;

import com.github.javafaker.Faker;
import generator.CreateUserGenerator;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import pojo.CreateUser;
import pojo.LoginUser;

import static org.hamcrest.core.IsEqual.equalTo;

@DisplayName("Авторизация пользователя")
public class UserLoginTest extends UserBaseTest {

    CreateUser request;
    ValidatableResponse response;
    ValidatableResponse loginResponse;
    static Faker faker = Faker.instance();


    @Test
    @DisplayName("Авторизация с корректными данными")
    @Description("Ожидание ответа 200")
    public void loginUserAllCorrectParameters() {

        request = CreateUserGenerator.getRandomNewUserGenerator();
        response = UserClient.createNewUser(request);

        response.statusCode(200)
                .and()
                .assertThat().body("success", equalTo(true));

        loginResponse = UserClient.userAuthorization(new LoginUser(request.getEmail(), request.getPassword()));
        loginResponse.statusCode(200)
                .and()
                .assertThat().body("success", equalTo(true));
    }

    @Test
    @DisplayName("Авторизация пользователя с неверным логином и паролем")
    @Description("Ожидание ответа 401")
    public void loginUserIncorrectDate() {

        loginResponse = UserClient.userAuthorization(new LoginUser(faker.internet().emailAddress(), faker.name().firstName()));
        loginResponse.statusCode(401)
                .and()
                .assertThat().body("message", equalTo("email or password are incorrect"));
    }

}
