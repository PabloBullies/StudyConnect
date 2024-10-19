package pb.studyconnect.server.integration;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class StudentIntegrationTest extends BaseIntegrationTest {

    @Test
    public void createStudent() {
        Response response = given().header("Content-Type", "application/json")
                .and()
                .body("""
            {
              "name": "Абоба Сигмович",
              "email": "aboba@gmail.ru",
              "tgNickname": "@sigma",
              "scientificInterests": ["CDM-16", "LLM","OS"],
              "skills": ["Assembler","vim","linux"],
              "department": "кафедра систем информатики",
              "initiativeTheme": "Генерация аssembler кода с использованием GPT"
            }
            """)
                .when()
                .post("/profiles/students")
                .then()
                .extract().response();

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals("Абоба Сигмович", response.jsonPath().getString("name"));
    }
    @Test
    public void createIncompleteStudent() {
        Response response = given().header("Content-Type", "application/json")
                .and()
                .body("""
                        {
                          "name": "Абоба Сигмович",
                          "email": "aboba@gmail.ru",
                          "tgNickname": "@sigma",
                          "scientificInterests": ["CDM-16", "LLM","OS"],
                          "skills": ["Assembler","vim","linux"]
                        }
                        """)
                .when()
                .post("/profiles/students")
                .then()
                .extract().response();
        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals("Абоба Сигмович", response.jsonPath().getString("name"));
    }
    @Test
    public void checkValidationMin3ElementsAtList() {
        // Ошибка, тк должно быть как минимум 3 элемента в scientificInterests
        Response response = given().header("Content-Type", "application/json")
                .and()
                .body("""
                        {
                          "name": "Абоба Сигмович",
                          "email": "aboba@gmail.ru",
                          "tgNickname": "@sigma",
                          "scientificInterests": ["CDM-16", "LLM"],
                          "skills": ["Assembler","vim", "linux"]
                        }
                        """)
                .when()
                .post("/profiles/students")
                .then()
                .extract().response();
        Assertions.assertNotEquals(200, response.statusCode());
    }
    @Test
    public void checkValidationEmail() {
        Response response = given().header("Content-Type", "application/json")
                .and()
                .body("""
                        {
                          "name": "Абоба Сигмович",
                          "email": "aboba",
                          "tgNickname": "@sigma",
                          "scientificInterests": ["CDM-16", "LLM","OS"],
                          "skills": ["Assembler","vim", "linux"]
                        }
                        """)
                .when()
                .post("/profiles/students")
                .then()
                .extract().response();

        Assertions.assertNotEquals(200, response.statusCode());
    }

    @Test
    public void checkValidationEmail2() {
        Response response = given().header("Content-Type", "application/json")
                .and()
                .body("""
                        {
                          "name": "Абоба Сигмович",
                          "email": "not@boba",
                          "tgNickname": "@sigma",
                          "scientificInterests": ["CDM-16", "LLM","OS"],
                          "skills": ["Assembler","vim", "linux"]
                        }
                        """)
                .when()
                .post("/profiles/students")
                .then()
                .extract().response();

        Assertions.assertNotEquals(200, response.statusCode());
    }
    @Test
    public void checkValidationTgNickname() {
        Response response = given().header("Content-Type", "application/json")
                .and()
                .body("""
                        {
                          "name": "Абоба Сигмович",
                          "email": "aboba@gmail.ru",
                          "tgNickname": "@SigmaMan",
                          "scientificInterests": ["CDM-16", "LLM","OS"],
                          "skills": ["Assembler","vim", "linux"]
                        }
                        """)
                .when()
                .post("/profiles/students")
                .then()
                .extract().response();

        Assertions.assertEquals(200, response.statusCode());
    }
    @Test
    public void checkValidationTgNickname2() {
        Response response = given().header("Content-Type", "application/json")
                .and()
                .body("""
                        {
                          "name": "Абоба Сигмович",
                          "email": "aboba@gmail.ru",
                          "tgNickname": "sigma",
                          "scientificInterests": ["CDM-16", "LLM","OS"],
                          "skills": ["Assembler","vim", "linux"]
                        }
                        """)
                .when()
                .post("/profiles/students")
                .then()
                .extract().response();

        Assertions.assertNotEquals(200, response.statusCode());
    }
    @Test
    public void checkValidationName() {
        // Только буквы!
        Response response = given().header("Content-Type", "application/json")
                .and()
                .body("""
                        {
                          "name": "Аб!@#$%бa 001",
                          "email": "aboba@gmail.ru",
                          "tgNickname": "sigma",
                          "scientificInterests": ["CDM-16", "LLM","OS"],
                          "skills": ["Assembler","vim", "linux"]
                        }
                        """)
                .when()
                .post("/profiles/students")
                .then()
                .extract().response();

        Assertions.assertNotEquals(200, response.statusCode());
    }
}