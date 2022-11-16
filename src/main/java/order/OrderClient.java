package order;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import pojo.CreateOrder;
import steps.Client;

import java.util.List;

public class OrderClient extends Client {

    private static final String INGREDIENTS = "ingredients";
    private static final String ORDERS = "orders";

    @Step("Получение ингридиентов")
    public static List<String> getIngredients() {
        return RestAssured.given()
                .spec(getDefaultRequestSpec())
                .when()
                .get(INGREDIENTS)
                .then().log().ifError()
                .extract().path("data._id");
    }

    @Step("Создание заказа с логином")
    public static ValidatableResponse creatingOrderWithLogin(CreateOrder ingredients, String accessToken) {
        return RestAssured.given()
                .spec(getDefaultRequestSpec())
                .header("Authorization", accessToken)
                .body(ingredients)
                .when()
                .post(ORDERS)
                .then()
                .log().ifError();
    }

    @Step("Создание заказа без логина")
    public static ValidatableResponse creatingOrderWithOutLogin(CreateOrder ingredients) {
        return RestAssured.given()
                .spec(getDefaultRequestSpec())
                .body(ingredients)
                .when()
                .post(ORDERS)
                .then()
                .log().ifError();
    }

    @Step("Получение заказа пользователя без авторизации")
    public static ValidatableResponse receivingOrderWithOutAuthorization() {
        return RestAssured.given()
                .spec(getDefaultRequestSpec())
                .when()
                .get(ORDERS)
                .then()
                .log().ifError();
    }

    @Step("Получение заказа пользователя с авторизацией")
    public static ValidatableResponse receivingOrderWithAuthorization(String accessToken) {
        return RestAssured.given()
                .spec(getDefaultRequestSpec())
                .header("Authorization", accessToken)
                .when()
                .get(ORDERS)
                .then()
                .log().ifError();
    }
}
