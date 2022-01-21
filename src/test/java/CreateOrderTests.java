import io.restassured.response.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class CreateOrderTests {

    UserClient userClient;
    String accessToken;

    @Before
    public void setUp(){
        userClient = new UserClient();
    }

    @Test
    public void successCreateOrder() {
        User user = User.getRandom();
        userClient.create(user);
        Response responseLogin = userClient.login(new UserCredentials(user.email, user.password));
        accessToken = responseLogin.path("accessToken");
        Boolean success = userClient.createOrder(new Ingredients().getIngredients(), accessToken);
        Assert.assertEquals(true, success);
    }

    @Test
    public void сreateOrderWitiInvalideIngredients() {
        User user = User.getRandom();
        userClient.create(user);
        Response responseLogin = userClient.login(new UserCredentials(user.email, user.password));
        accessToken = responseLogin.path("accessToken");
        String message = userClient.createOrderFail(new Ingredients().getIncorrectIngredients(), accessToken);
        Assert.assertEquals("One or more ids provided are incorrect", message);
    }

    @Test
    public void successCreateOrderWhithoutIgredients() {
        User user = User.getRandom();
        userClient.create(user);
        Response responseLogin = userClient.login(new UserCredentials(user.email, user.password));
        accessToken = responseLogin.path("accessToken");
        String message = userClient.createOrderFail(new Ingredients().getNoIngredients(), accessToken);
        Assert.assertEquals("Ingredient ids must be provided", message);

    }

    @Test
    public void createOrderWithoutToken() {
        User user = User.getRandom();
        userClient.create(user);
        Response responseLogin = userClient.login(new UserCredentials(user.email, user.password));
        accessToken = responseLogin.path("accessToken");
        String message = userClient.createOrderWithoutToken(new Ingredients().getIngredients());
        Assert.assertEquals("You should be authorised", message);
    }

    @After
    public void tearDown(){
        userClient.delete(accessToken.substring(7));
    }
}
