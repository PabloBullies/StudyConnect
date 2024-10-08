package pb.studyconnect.server.integration;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import io.restassured.RestAssured;

public class StudentIntegrationTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost:8080";
    }

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
              "initiativeTheme": "Генерация аssembler кода с использованием GPT"
            }
            """)
                .when()
                .post("/profiles/students")
                .then()
                .extract().response();

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals("Абоба Сигмович", response.jsonPath().getString("name"));


        response = given().header("Content-Type", "application/json")
                .and()
                .body("""
            {
              "name": "Дмитрий Иртеров",
              "email": "fat@nsu.ru",
              "tgNickname": "@fatBrother",
              "scientificInterests": ["ОСИ", "peer-to-peer", "CDM-16"],
              "diplomaTopics": ["Системы оркестрации контейнеров бла бла бла"],
              "department": "кафедра систем информатики"
            }
            """)
                .when()
                .post("/profiles/students")
                .then()
                .extract().response();
        Assertions.assertNotEquals(200, response.statusCode());
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

        response = given().header("Content-Type", "application/json")
                .and()
                .body("""
                        {
                          "name": "Абоба Сигмович",
                          "email": "aboba@gmail.ru",
                          "tgNickname": "@sigma",
                          "scientificInterests": ["CDM-16", "LLM","OS"],
                          "skills": ["Assembler","vim"]
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

        response = given().header("Content-Type", "application/json")
                .and()
                .body("""
                        {
                          "name": "Абоба Сигмович",
                          "email": "no@boba",
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
        Response response = given().header("Content-Type", "application/json")
                .and()
                .body("""
                        {
                          "name": "Абоба 001",
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
        response = given().header("Content-Type", "application/json")
                .and()
                .body("""
                        {
                          "name": "Аб!@#$%бa",
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