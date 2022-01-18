import org.apache.commons.lang3.RandomStringUtils;

/**
 * * Created by a.khudyakova on 17.01.2022.
 */
public class User {

    public static final String EMAIL_POSTFIX = "@yandex.ru";
    public String email;
    public String password;
    public String name;

    public User(String login, String password, String name) {
        this.email = login;
        this.password = password;
        this.name = name;
    }

    public static User getRandom() {
        final String email = RandomStringUtils.randomAlphabetic(10) + EMAIL_POSTFIX;
        final String password = RandomStringUtils.randomAlphabetic(10);
        final String name = RandomStringUtils.randomAlphabetic(10);
        return new User(email, password, name);
    }

    public static User getRandomWithoutPass() {
        final String email = RandomStringUtils.randomAlphabetic(10) + EMAIL_POSTFIX;
        final String name = RandomStringUtils.randomAlphabetic(10);
        return new User(email, "", name);
    }
}
