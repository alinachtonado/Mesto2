import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static sun.jvm.hotspot.utilities.AddressOps.greaterThan;

import io.restassured.matcher.RestAssuredMatchers;
import org.hamcrest.Matchers;


public class ListOfOrdersTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
    }

    @Test
    public void getListOfOrdersTest() {
        given()
                .header("Content-type", "application/json")
                .when()
                .post("/api/v1/orders?courierId=1")
                .then().statusCode(200).and().body("orders.size()", greaterThan(0));
    }
}
