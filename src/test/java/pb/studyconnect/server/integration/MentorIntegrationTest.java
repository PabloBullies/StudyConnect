package pb.studyconnect.server.integration;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import io.restassured.RestAssured;

import java.io.IOException;
import java.util.Properties;

public class MentorIntegrationTest extends BaseIntegrationTest {

    @Test
    public void createMentor() {
        Response response = given().header("Content-Type", "application/json")
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
                .post("/profiles/mentors")
                .then()
                .extract().response();

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals("Дмитрий Иртеров", response.jsonPath().getString("name"));

        response = given().header("Content-Type", "application/json")
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
                .post("/profiles/mentors")
                .then()
                .extract().response();

        Assertions.assertNotEquals(200, response.statusCode());
    }
    @Test
    public void checkValidationMin3ElementsAtList() {
        Response response = given().header("Content-Type", "application/json")
                .and()
                .body("""
            {
              "name": "Дмитрий Иртеров",
              "email": "fat@nsu.ru",
              "tgNickname": "@fatBrother",
              "scientificInterests": ["ОСИ", "peer-to-peer"],
              "diplomaTopics": ["Системы оркестрации контейнеров бла бла бла"],
              "department": "кафедра систем информатики"
            }
            """)
                .when()
                .post("/profiles/mentors")
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
              "name": "Дмитрий Иртеров",
              "email": "fatnsu.ru",
              "tgNickname": "@fatBrother",
              "scientificInterests": ["ОСИ", "peer-to-peer"],
              "diplomaTopics": ["Системы оркестрации контейнеров бла бла бла"],
              "department": "кафедра систем информатики"
            }
            """)
                .when()
                .post("/profiles/mentors")
                .then()
                .extract().response();

        Assertions.assertNotEquals(200, response.statusCode());

        response = given().header("Content-Type", "application/json")
                .and()
                .body("""
            {
              "name": "Дмитрий Иртеров",
              "email": "fat!nsu",
              "tgNickname": "@fatBrother",
              "scientificInterests": ["ОСИ", "peer-to-peer"],
              "diplomaTopics": ["Системы оркестрации контейнеров бла бла бла"],
              "department": "кафедра систем информатики"
            }
            """)
                .when()
                .post("/profiles/mentors")
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
              "name": "Дмитрий Иртеров",
              "email": "fatnsu.ru",
              "tgNickname": "fatBr@ther",
              "scientificInterests": ["ОСИ", "peer-to-peer"],
              "diplomaTopics": ["Системы оркестрации контейнеров бла бла бла"],
              "department": "кафедра систем информатики"
            }
            """)
                .when()
                .post("/profiles/mentors")
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
              "name": "Дмитрий Иртеров!!!",
              "email": "fatnsu.ru",
              "tgNickname": "fatBr@ther",
              "scientificInterests": ["ОСИ", "peer-to-peer"],
              "diplomaTopics": ["Системы оркестрации контейнеров бла бла бла"],
              "department": "кафедра систем информатики"
            }
            """)
                .when()
                .post("/profiles/mentors")
                .then()
                .extract().response();

        Assertions.assertNotEquals(200, response.statusCode());
    }
}