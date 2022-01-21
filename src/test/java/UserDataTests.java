import io.restassured.response.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;


public class UserDataTests {

    UserClient userClient;
    String accessToken;

    @Before
    public void setUp(){
        userClient = new UserClient();
    }

    @Test
    public void changeName() {
        User user = User.getRandom();
        userClient.create(user);
        Response responseLogin = userClient.login(new UserCredentials(user.email, user.password));
        accessToken = responseLogin.path("accessToken");
        String newName = "тест";
        Map<String, String> name = userClient.change(new User(user.email, user.password, newName), accessToken);
        Assert.assertEquals(newName, name.get("name"));
    }

    @Test
    public void changeEmail() {
        User user = User.getRandom();
        Response responseCreate = userClient.create(user);
        accessToken = responseCreate.path("accessToken");
        String newEmail = "тест@yandex.ru";
        Map<String, String> name = userClient.change(new User(newEmail, user.password, user.name), accessToken);
        Assert.assertEquals(newEmail, name.get("email"));
    }

    @Test
    public void changeEmailWithoutLogin() {
        User user = User.getRandom();
        Response responseCreate = userClient.create(user);
        accessToken = responseCreate.path("accessToken");
        String newEmail = "тест@yandex.ru";
        Map<String, String> name = userClient.change(new User(newEmail, user.password, user.name), accessToken);
        Assert.assertEquals(newEmail, name.get("email"));
    }

    @Test
    public void changePassword() {
        User user = User.getRandom();
        userClient.create(user);
        Response responseLogin = userClient.login(new UserCredentials(user.email, user.password));
        accessToken = responseLogin.path("accessToken");
        String newPass = "тест";
        Boolean success = userClient.changePass(new User(user.email, newPass, user.name), accessToken);
        Assert.assertEquals(true, success);
    }

    @Test
    public void changePasswordWithoutLogin() {
        User user = User.getRandom();
        Response responseCreate = userClient.create(user);
        accessToken = responseCreate.path("accessToken");
        String newPass = "тест";
        Boolean success = userClient.changePass(new User(user.email, newPass, user.name), accessToken);
        Assert.assertEquals(true, success);
    }

    @Test
    public void changePasswordWithoutToken() {
        User user = User.getRandom();
        Response responseCreate = userClient.create(user);
        accessToken = responseCreate.path("accessToken");
        String newPass = "тест";
        Boolean success = userClient.changeWithoutToken(new User(user.email, newPass, user.name));
        Assert.assertEquals(false, success);
    }

    @After
    public void tearDown(){
        userClient.delete(accessToken.substring(7));
    }
}
