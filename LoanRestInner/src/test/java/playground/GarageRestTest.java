package playground;

import com.FunctionalTest;
import org.junit.jupiter.api.Test;

import static com.jayway.restassured.RestAssured.given;

public class GarageRestTest extends FunctionalTest {
    @Test
    public void basicPingTest() {
        given().when().get("/test1").then().statusCode(200);
    }
}
