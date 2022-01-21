import io.restassured.response.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;



public class RegistrationTests {

    UserClient userClient;
    String accessToken;

    @Before
    public void setUp(){
        userClient = new UserClient();
    }

    @Test
    public void createAUniqueUser() {
        User user = User.getRandom();
        Response response = userClient.create(user);
        accessToken = response.path("accessToken");
        Boolean success = response.path("success");
        Assert.assertEquals(200, response.statusCode());
        Assert.assertEquals(true, success);
    }

    @Test
    public void createANonUniqueUser() {
        User user = User.getRandom();
        Response response = userClient.create(user);
        accessToken = response.path("accessToken");
        Response response2 = userClient.create(user);
        String message = response2.path("message");
        Assert.assertEquals(403, response2.statusCode());
        Assert.assertEquals("User already exists", message);
    }

    @After
    public void tearDown(){
        userClient.delete(accessToken.substring(7));
    }
}
