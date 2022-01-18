import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class RegistrationNegativeTests {

    UserClient userClient;

    @Before
    public void setUp(){
        userClient = new UserClient();
    }

    @Test
    public void createAUniqueUserWithoutPass() {
        User user = User.getRandomWithoutPass();
        Response response = userClient.create(user);
        String message = response.path("message");
        Assert.assertEquals(403, response.statusCode());
        Assert.assertEquals("Email, password and name are required fields", message);
    }
}
