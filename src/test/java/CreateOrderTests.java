import io.restassured.response.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;


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
        ArrayList<String> ingredients = new ArrayList<>();
        ingredients.add("\"61c0c5a71d1f82001bdaaa6d\"");
        ingredients.add("\"61c0c5a71d1f82001bdaaa6f\"");
        Boolean success = userClient.createOrder("{\"ingredients\": " + ingredients.toString()+ "}", accessToken);
        Assert.assertEquals(true, success);
    }

    @Test
    public void сreateOrderWitiInvalideIngredients() {
        User user = User.getRandom();
        userClient.create(user);
        Response responseLogin = userClient.login(new UserCredentials(user.email, user.password));
        accessToken = responseLogin.path("accessToken");
        ArrayList<String> ingredients = new ArrayList<>();
        ingredients.add("\"60d3b41abdacab0026a73311\"");
        ingredients.add("\"609646e4dc916e00276b2811\"");
        String message = userClient.createOrderFail("{\"ingredients\": " + ingredients.toString()+ "}", accessToken);
        Assert.assertEquals("One or more ids provided are incorrect", message);
    }

    @Test
    public void successCreateOrderWhithoutIgredients() {
        User user = User.getRandom();
        userClient.create(user);
        Response responseLogin = userClient.login(new UserCredentials(user.email, user.password));
        accessToken = responseLogin.path("accessToken");
        String message = userClient.createOrderFail("{\"ingredients\": [] }", accessToken);
        Assert.assertEquals("Ingredient ids must be provided", message);

    }

    @Test
    public void createOrderWithoutToken() {
        User user = User.getRandom();
        userClient.create(user);
        Response responseLogin = userClient.login(new UserCredentials(user.email, user.password));
        accessToken = responseLogin.path("accessToken");
        ArrayList<String> ingredients = new ArrayList<>();
        ingredients.add("\"61c0c5a71d1f82001bdaaa6d\"");
        ingredients.add("\"61c0c5a71d1f82001bdaaa6f\"");
        String message = userClient.createOrderWithoutToken("{\"ingredients\": " + ingredients.toString()+ "}");
        Assert.assertEquals("You should be authorised", message);
    }

    @After
    public void tearDown(){
        userClient.delete(accessToken.substring(7));
    }
}
