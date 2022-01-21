import io.restassured.response.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class ReceivingOrdersTests {

    UserClient userClient;
    String accessToken;

    @Before
    public void setUp(){
        userClient = new UserClient();
    }

    @Test
    public void successGetOrder() {
        User user = User.getRandom();
        userClient.create(user);
        Response responseLogin = userClient.login(new UserCredentials(user.email, user.password));
        accessToken = responseLogin.path("accessToken");
        Boolean success = userClient.getOrders(accessToken);
        Assert.assertEquals(true, success);
    }

    @Test
    public void getOrderWithoutToken() {
        User user = User.getRandom();
        userClient.create(user);
        Response responseLogin = userClient.login(new UserCredentials(user.email, user.password));
        accessToken = responseLogin.path("accessToken");
        String message = userClient.getOrdersWithoutToken();
        Assert.assertEquals("You should be authorised", message);
    }


    @After
    public void tearDown(){
        userClient.delete(accessToken.substring(7));
    }
}
