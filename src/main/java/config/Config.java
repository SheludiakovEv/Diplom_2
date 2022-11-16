package config;

public class Config {

    //В этом классе вынес URL, чтобы сократить код

    private static final String BASE_URL = "https://stellarburgers.nomoreparties.site/api/";

    public static String getBaseUrl() {
        return BASE_URL;

    }
}
