package user;

import generator.CreateUserGenerator;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Assert;

import static org.hamcrest.core.IsEqual.equalTo;

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
        responseAfterChangeField = CreateUser.editEmailOfUserFiled();
        ValidatableResponse updateDataOfUser = UserClient.changeWithAuthorization(responseAfterChangeField, accessToken);

        String newEmail = updateDataOfUser.extract().path("user.email");
        Assert.assertNotEquals(CreateUser.editEmailOfUserFiled().getEmail(), newEmail);
    }

    @Test
    @DisplayName("Изменение поля Name у авторизованного пользователя")
    @Description("Ожидание ответа 200")
    public void changingNameFieldAuthorizedUser() {
        responseAfterChangeField = CreateUser.editNameOfUserFiled();
        ValidatableResponse updateDataOfUser = UserClient.changeWithAuthorization(responseAfterChangeField, accessToken);

        String newName = updateDataOfUser.extract().path("user.name");
        Assert.assertNotEquals(CreateUser.editNameOfUserFiled().getName(), newName);
    }

    @Test
    @DisplayName("Изменение поля Name без авторизацией")
    @Description("Ожидание ответа 401")
    public void changingNameFieldWithoutAuthorization() {
        responseAfterChangeField = CreateUser.editNameOfUserFiled();
        ValidatableResponse updateDataOfUser = UserClient.changeWithoutAuthorization(responseAfterChangeField);

        updateDataOfUser.assertThat()
                .statusCode(401)
                .and()
                .body("message", equalTo(ERROR_401));
    }

    @Test
    @DisplayName("Изменение поля Email без авторизацией")
    @Description("Ожидание ответа 401")
    public void changingEmailFieldWithoutAuthorization() {
        responseAfterChangeField = CreateUser.editEmailOfUserFiled();
        ValidatableResponse updateDataOfUser = UserClient.changeWithoutAuthorization(responseAfterChangeField);

        updateDataOfUser.assertThat()
                .statusCode(401)
                .and()
                .body("message", equalTo(ERROR_401));
    }

    @Test
    @DisplayName("Изменение полей mail и name без авторизацией")
    @Description("Ожидание ответа 401")
    public void updateUserNameAndEmailWithoutAuthorization() {
        responseAfterChangeField = CreateUser.editNameAndEmailOfUserFiled();
        ValidatableResponse updateDataOfUser = UserClient.changeWithoutAuthorization(responseAfterChangeField);

        String newEmail = updateDataOfUser.extract().path("user.email");
        String newName = updateDataOfUser.extract().path("user.name");
        Assert.assertNotEquals(CreateUser.editEmailOfUserFiled().getEmail(), newEmail);
        Assert.assertNotEquals(CreateUser.editNameOfUserFiled().getName(), newName);
    }

    @Test
    @DisplayName("Изменение поля password без авторизацией")
    @Description("Ожидание ответа 401")
    public void updateUserPasswordWithoutAuthorization() {
        responseAfterChangeField = CreateUser.editNameAndEmailOfUserFiled();
        ValidatableResponse updateDataOfUser = UserClient.changeWithoutAuthorization(responseAfterChangeField);

        String newPassword = updateDataOfUser.extract().path("user.password");
        Assert.assertNotEquals(CreateUser.editPasswordOfUserFiled(), newPassword);
    }
}