import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;

public class CourierLoginTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
    }

    @Test
    public void courierCanAuth() {
        File json = new File("src/test/resources/courierAlinaAuth.json");
        given()
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .post("/api/v1/courier/login")
                .then().assertThat().body("id", notNullValue())
                .statusCode(200);
    }

    @Test
    public void cannotAuthWithoutLoginTest() {
        File json = new File("src/test/resources/authWithoutLogin.json");
        given()
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .post("/api/v1/courier/login")
                .then().statusCode(400);
    }

    @Test
    public void cannotAuthWithoutPasswordTest() {
        File json = new File("src/test/resources/authWithoutPassword.json");
        given()
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .post("/api/v1/courier/login")
                .then().statusCode(400);
    }

    @Test
    public void authWithIncorrectData() {
        File json = new File("src/test/resources/authWithIncorrectData.json");
        given()
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .post("/api/v1/courier/login")
                .then().statusCode(404);
    }


}