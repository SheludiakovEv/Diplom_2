package user;

import com.github.javafaker.Faker;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;

public class UserBaseTest {

    static Faker faker = Faker.instance();
    String accessToken;

    @After
    @DisplayName("Удаление пользователя")
    public void tearDown() {
        if (accessToken != null) {
            UserClient.userDelete(accessToken);
        }
    }
}
