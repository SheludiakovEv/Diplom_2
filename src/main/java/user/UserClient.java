package user;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import pojo.CreateUser;
import pojo.LoginUser;
import steps.Client;

public class UserClient extends Client {

    private static final String AUTH = "auth/";
    private static final String REGISTER = AUTH + "register";
    private static final String LOGIN = AUTH + "login";
    private static final String USER = AUTH + "user";

    @Step("Корректное создание пользователя")
    public static ValidatableResponse createNewUser(CreateUser body) {
        return RestAssured.given()
                .spec(getDefaultRequestSpec())
                .body(body)
                .when()
                .post(REGISTER)
                .then().log().all();
    }

    @Step("Создание пользователя не заполнив поле Пароль")
    public static ValidatableResponse createNewUserNotFilledPassword(CreateUser body) {
        return RestAssured.given()
                .spec(getDefaultRequestSpec())
                .body(body)
                .when()
                .post(REGISTER)
                .then().log().all();
    }

    @Step("Корректная авторизация")
    public static ValidatableResponse userAuthorization(LoginUser body) {
        return RestAssured.given()
                .spec(getDefaultRequestSpec())
                .body(body)
                .when()
                .post(LOGIN)
                .then().log().all();
    }

    @Step("Удаление пользователя")
    public static ValidatableResponse userDelete(String accessToken) {
        return RestAssured.given()
                .spec(getDefaultRequestSpec())
                .header("Authorization", accessToken)
                .when()
                .delete(USER)
                .then().log().all()
                .assertThat()
                .statusCode(202);
    }

    @Step("Изменить данные пользователя с авторизацией")
    public static ValidatableResponse changeWithAuthorization(CreateUser user, String accessToken) {
        return RestAssured.given()
                .spec(getDefaultRequestSpec())
                .header("Authorization", accessToken)
                .body(user)
                .when()
                .patch(USER)
                .then()
                .log().ifError()
                .statusCode(200)
                .and();
    }

    @Step("Изменить данные пользователя без авторизации")
    public static ValidatableResponse changeWithoutAuthorization(CreateUser user) {
        return RestAssured.given()
                .spec(getDefaultRequestSpec())
                .body(user)
                .when()
                .patch(USER)
                .then()
                .log().ifError()
                .statusCode(401)
                .and();
    }
}
