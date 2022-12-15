package user;

import generator.CreateUserGenerator;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;

import static org.hamcrest.core.IsEqual.equalTo;
import static pojo.CreateUser.editEmailOfUserFiled;
import static pojo.CreateUser.editNameOfUserFiled;

import org.junit.Before;
import org.junit.Test;
import pojo.CreateUser;

@DisplayName("Изменение данных пользователя")
public class UpdateUserDataTest extends UserBaseTest {

    public static final String ERROR_401 = "You should be authorised";
    CreateUser request;
    ValidatableResponse response;
    CreateUser responseAfterChangeField;

    @Before
    @DisplayName("Создать юзера")
    public void initialAction() {
        request = CreateUserGenerator.getRandomNewUserGenerator();
        response = UserClient.createNewUser(request);
        accessToken = response.extract().path("accessToken").toString();
    }

    @Test
    @DisplayName("Изменение поля Email у авторизованного пользователя")
    @Description("Ожидание ответа 200")
    public void changingEmailFieldAuthorizedUser() {
        responseAfterChangeField = editEmailOfUserFiled();
        ValidatableResponse updateDataOfUser = UserClient.changeWithAuthorization(responseAfterChangeField, accessToken);

        updateDataOfUser.assertThat()
                .body("user.email", equalTo(responseAfterChangeField.getEmail()));
    }

    @Test
    @DisplayName("Изменение поля Name у авторизованного пользователя")
    @Description("Ожидание ответа 200")
    public void changingNameFieldAuthorizedUser() {
        responseAfterChangeField = editNameOfUserFiled();
        ValidatableResponse updateDataOfUser = UserClient.changeWithAuthorization(responseAfterChangeField, accessToken);

        updateDataOfUser.assertThat()
                .body("user.name", equalTo(responseAfterChangeField.getName()));
    }

    @Test
    @DisplayName("Изменение поля Name без авторизацией")
    @Description("Ожидание ответа 401")
    public void changingNameFieldWithoutAuthorization() {
        responseAfterChangeField = editNameOfUserFiled();
        ValidatableResponse updateDataOfUser = UserClient.changeWithoutAuthorization(responseAfterChangeField);

        updateDataOfUser.assertThat()
                .body("message", equalTo(ERROR_401));
    }

    @Test
    @DisplayName("Изменение поля Email без авторизацией")
    @Description("Ожидание ответа 401")
    public void changingEmailFieldWithoutAuthorization() {
        responseAfterChangeField = editEmailOfUserFiled();
        ValidatableResponse updateDataOfUser = UserClient.changeWithoutAuthorization(responseAfterChangeField);

        updateDataOfUser.assertThat()
                .body("message", equalTo(ERROR_401));
    }

    @Test
    @DisplayName("Изменение полей mail и name без авторизацией")
    @Description("Ожидание ответа 401")
    public void updateUserNameAndEmailWithoutAuthorization() {
        responseAfterChangeField = CreateUser.editNameAndEmailOfUserFiled();
        ValidatableResponse updateDataOfUser = UserClient.changeWithoutAuthorization(responseAfterChangeField);

        updateDataOfUser.assertThat()
                .body("message", equalTo(ERROR_401));
    }

    @Test
    @DisplayName("Изменение поля password без авторизацией")
    @Description("Ожидание ответа 401")
    public void updateUserPasswordWithoutAuthorization() {
        responseAfterChangeField = CreateUser.editNameAndEmailOfUserFiled();
        ValidatableResponse updateDataOfUser = UserClient.changeWithoutAuthorization(responseAfterChangeField);

        updateDataOfUser.assertThat()
                .body("message", equalTo(ERROR_401));
    }
}