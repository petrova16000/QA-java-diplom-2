import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LoginNegativeTests {

    UserClient userClient;

    @Before
    public void setUp(){
        userClient = new UserClient();
    }
    @Test
    public void loginWithIncirrectLoginPass() {
        final String login = RandomStringUtils.randomAlphabetic(10) + "@yandex.ru";
        final String password = RandomStringUtils.randomAlphabetic(10);
        Response responseLogin = userClient.login(new UserCredentials(login, password));
        String message = responseLogin.path("message");
        Assert.assertEquals(401, responseLogin.statusCode());
        Assert.assertEquals("email or password are incorrect", message);
    }
}
