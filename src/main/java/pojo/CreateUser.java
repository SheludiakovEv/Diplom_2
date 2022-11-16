package pojo;

import com.github.javafaker.Faker;
import io.qameta.allure.Step;
import lombok.Data;

@Data
public class CreateUser {

    private String email;
    private String password;
    private String name;

    public CreateUser() {
    }

    public CreateUser(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    @Step("Пользователь для смены поля email")
    public static CreateUser editEmailOfUserFiled() {
        return new CreateUser(Faker.instance().internet().emailAddress(), null,
                null);
    }

    @Step("Пользователь для смены name")
    public static CreateUser editNameOfUserFiled() {
        return new CreateUser(null, null,
                Faker.instance().name().firstName());
    }

    @Step("Пользователь для полей смены email и name")
    public static CreateUser editNameAndEmailOfUserFiled() {
        return new CreateUser(Faker.instance().internet().emailAddress(), null,
                Faker.instance().name().firstName());
    }

    @Step("Пользователь для полей смены email и name")
    public static CreateUser editPasswordOfUserFiled() {
        return new CreateUser(null, Faker.instance().internet().password(),
                null);
    }
}
