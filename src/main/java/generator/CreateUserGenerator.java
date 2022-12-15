package generator;

import io.qameta.allure.Step;
import pojo.CreateUser;
import com.github.javafaker.Faker;

public class CreateUserGenerator {

    static Faker faker = Faker.instance();

    @Step("Создание пользователя")
    public static CreateUser getRandomNewUserGenerator() {
        CreateUser createUser = new CreateUser();
        createUser.setEmail(faker.internet().emailAddress());
        createUser.setPassword(faker.number().toString());
        createUser.setName(faker.name().firstName());
        return createUser;
    }

    @Step("Создание пользователя не заполнив поле Password")
    public static CreateUser getRandomNotFilledPassword() {
        CreateUser createUser = new CreateUser();
        createUser.setEmail(faker.internet().emailAddress());
        createUser.setName(faker.name().firstName());
        return createUser;
    }

    @Step("Создание пользователя не заполнив поле Name")
    public static CreateUser getRandomNotFilledName() {
        CreateUser createUser = new CreateUser();
        createUser.setEmail(faker.internet().emailAddress());
        createUser.setPassword(faker.number().toString());
        return createUser;
    }

    @Step("Создание пользователя не заполнив поле Email")
    public static CreateUser getRandomNotFilledEmail() {
        CreateUser createUser = new CreateUser();
        createUser.setPassword(faker.number().toString());
        createUser.setName(faker.name().firstName());
        return createUser;
    }
}
