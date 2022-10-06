import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CourierCreateTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
    }

    @Test
    public void canCreateTest() {
        String json = getRandomCourierJson();
         given()
            .header("Content-type", "application/json")
            .body(json)
            .when()
            .post("/api/v1/courier")
            .then().assertThat().body("ok", equalTo(true))
            .and()
            .statusCode(201);
    }

    @Test
    public void cannotCreateDuplicateTest() {
        String json = getRandomCourierJson();
        given()
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .post("/api/v1/courier")
                .then().statusCode(201);

        given()
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .post("/api/v1/courier")
                .then().statusCode(409);
    }

    private static String getRandomCourierJson(){
        String json = String.format("{\"login\": \"a%s@gmail.com\"," +
                        "  \"password\": \"somepwd\", \"firstName\": \"Yaya\"}",
                java.util.UUID.randomUUID());
        return json;
    }

    @Test
    public void cannotCreateCourierWithoutLoginTest() {
        File json = new File("src/test/resources/newCourierWithoutLogin.json");
        given()
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .post("/api/v1/courier")
                .then().statusCode(400);
    }

    @Test
    public void cannotCreateCourierWithoutPasswordTest() {
        File json = new File("src/test/resources/newCourierWithoutPassword.json");
        given()
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .post("/api/v1/courier")
                .then().statusCode(400);
    }
}

