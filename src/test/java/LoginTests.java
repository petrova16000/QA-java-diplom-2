import io.restassured.response.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class LoginTests {

    UserClient userClient;
    String accessToken;

    @Before
    public void setUp(){
        userClient = new UserClient();
    }

    @Test
    public void successLogin() {
        User user = User.getRandom();
        userClient.create(user);
        Response responseLogin = userClient.login(new UserCredentials(user.email, user.password));
        accessToken = responseLogin.path("accessToken");
        Boolean success = responseLogin.path("success");
        Assert.assertEquals(200, responseLogin.statusCode());
        Assert.assertEquals(true, success);
    }

    @After
    public void tearDown(){
        userClient.delete(accessToken.substring(7));
    }
}
