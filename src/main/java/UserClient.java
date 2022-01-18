import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;


public class UserClient extends RestAssuredClient {

    private static final String REGISTER_PATH = "auth/register";
    private static final String LOGIN_PATH = "auth/login";
    private static final String USER_PATH = "auth/user";
    private static final String ORDER_PATH = "orders";

    @Step("Регистрация")
    public Response create(User user) {
        Response response = given()
                .spec(getBaseSpec())
                .body(user)
                .log().all()
                .when()
                .post(REGISTER_PATH);
           return response;
    }

    @Step("Логин")
    public Response login(UserCredentials userCredentials) {
        Response response = given()
                .spec(getBaseSpec())
                .body(userCredentials)
                .log().all()
                .when()
                .post(LOGIN_PATH);
        return response;
    }

    @Step("Изменение данных")
    public Map<String, String> change(User user, String token) {
        return given()
                .header("Authorization", token)
                .spec(getBaseSpec())
                .body(user)
                .log().all()
                .when()
                .patch(USER_PATH)
                 .then()
                .log().all()
                .statusCode(200)
                .extract()
                .path("user");
    }

    @Step("Изменение данных")
    public Boolean changePass(User user, String token) {
        return given()
                .header("Authorization", token)
                .spec(getBaseSpec())
                .body(user)
                .log().all()
                .when()
                .patch(USER_PATH)
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .path("success");
    }

    @Step("Изменение данных")
    public Boolean changeWithoutToken(User user) {
        return given()
                .spec(getBaseSpec())
                .body(user)
                .log().all()
                .when()
                .patch(USER_PATH)
                .then()
                .log().all()
                .statusCode(401)
                .extract()
                .path("success");
    }

    @Step("Создание заказа")
    public Boolean createOrder(String ingredients, String token) {
        return given()
                .header("Authorization", token)
                .spec(getBaseSpec())
                .body(ingredients)
                .log().all()
                .when()
                .post(ORDER_PATH)
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .path("success");
    }

    @Step("Создание заказа")
    public Boolean getOrders(String token) {
        return given()
                .header("Authorization", token)
                .spec(getBaseSpec())
                .log().all()
                .when()
                .get(ORDER_PATH)
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .path("success");
    }

    @Step("Создание заказа")
    public String getOrdersWithoutToken() {
        return given()
                .spec(getBaseSpec())
                .log().all()
                .when()
                .get(ORDER_PATH)
                .then()
                .log().all()
                .statusCode(401)
                .extract()
                .path("message");
    }

    @Step("Создание заказа")
    public String createOrderFail(String ingredients, String token) {
        return given()
                .header("Authorization", token)
                .spec(getBaseSpec())
                .body(ingredients)
                .log().all()
                .when()
                .post(ORDER_PATH)
                .then()
                .log().all()
                .statusCode(400)
                .extract()
                .path("message");
    }


    @Step("Создание заказа")
    public String createOrderWithoutToken(String ingredients) {
        return given()
                .spec(getBaseSpec())
                .body(ingredients)
                .log().all()
                .when()
                .post(ORDER_PATH)
                .then()
                .log().all()
                .statusCode(401)
                .extract()
                .path("message");
    }

    @Step("Удаление")
    public void delete(String token) {
        given()
                .spec(getBaseSpec())
                .auth().oauth2(token)
                .log().all()
                .when()
                .delete("auth/user")
                .then()
                .log().all()
                .statusCode(202);
    }
}
