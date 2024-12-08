package pb.studyconnect.server.integration;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

import java.io.IOException;
import java.util.Properties;

public abstract class BaseIntegrationTest {

    @BeforeAll
    public static void setup() throws IOException {
        if (System.getProperty("master.host") != null) {
            RestAssured.baseURI = System.getProperty("master.host");
        } else {
            Properties prop = new Properties();
            prop.load(BaseIntegrationTest.class.getClassLoader().getResourceAsStream("application.properties"));
            RestAssured.baseURI = prop.getProperty("master.host");
        }
    }
}
