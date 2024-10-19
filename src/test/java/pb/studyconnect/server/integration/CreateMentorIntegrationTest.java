package pb.studyconnect.server.integration;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;


public class CreateMentorIntegrationTest extends BaseIntegrationTest {

    @Test
    public void createMentor() {
        // Создание научного руководителя
        Response response = given().header("Content-Type", "application/json")
                .and()
                .body("""
                        {
                          "name": "Дмитрий Иртеров",
                          "email": "fat@nsu.ru",
                          "tgNickname": "@fat_brother",
                          "scientificInterests": ["ОСИ", "peer-to-peer", "CDM-16"],
                          "department": "кафедра систем информатики",
                          "diplomaTopics": [{
                                "name": "Разработка модуля для системы оркестрации контейнеров",
                                "summary": "надо че то сделать",
                                "neededSkills": ["стрессоустойчивость"],
                                "scientificField": "системы оркестрации контейнеров"
                          }]
                        }
                        """)
                .when()
                .post("/profiles/mentors")
                .then()
                .extract().response();

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals("Дмитрий Иртеров", response.jsonPath().getString("name"));
    }
    @Test
    public void checkValidationMin3ElementsAtList() {
        // Ошибка, тк должно быть как минимум 3 элемента в scientificInterests
        Response response = given().header("Content-Type", "application/json")
                .and()
                .body("""
            {
              "name": "Дмитрий Иртеров",
              "email": "fat@nsu.ru",
              "tgNickname": "@fat_brother",
              "scientificInterests": ["ОСИ", "peer-to-peer"],
              "department": "кафедра систем информатики",
              "diplomaTopics": [{
                    "name": "Разработка модуля для системы оркестрации контейнеров",
                   "summary": "надо че то сделать",
                   "neededSkills": ["стрессоустойчивость"],
                   "scientificField": "системы оркестрации контейнеров"
              }]
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
                          "email": "fat@nsuru",
                          "tgNickname": "@fat_brother",
                          "scientificInterests": ["ОСИ", "peer-to-peer"],
                          "department": "кафедра систем информатики",
                          "diplomaTopics": [{
                                "name": "Разработка модуля для системы оркестрации контейнеров",
                               "summary": "надо че то сделать",
                               "neededSkills": ["стрессоустойчивость"],
                               "scientificField": "системы оркестрации контейнеров"
                          }]
                        }
                        """)
                .when()
                .post("/profiles/mentors")
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
                          "name": "Дмитрий Иртеров",
                          "email": "fat!nsu.ru",
                          "tgNickname": "@fat_brother",
                          "scientificInterests": ["ОСИ", "peer-to-peer"],
                          "department": "кафедра систем информатики",
                          "diplomaTopics": [{
                                "name": "Разработка модуля для системы оркестрации контейнеров",
                               "summary": "надо че то сделать",
                               "neededSkills": ["стрессоустойчивость"],
                               "scientificField": "системы оркестрации контейнеров"
                          }]
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
              "tgNickname": "@FatBrother",
              "scientificInterests": ["ОСИ", "peer-to-peer"],
              "diplomaTopics": ["Системы оркестрации контейнеров бла бла бла"],
              "department": "кафедра систем информатики"
            }
            """)
                .when()
                .post("/profiles/mentors")
                .then()
                .extract().response();

        Assertions.assertEquals(200, response.statusCode());
    }
    @Test
    public void checkValidationTgNickname2() {
        // формат "@nickname"
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
    public void checkValidationTgNickname3() {
        // "-" запрещено
        Response response = given().header("Content-Type", "application/json")
                .and()
                .body("""
            {
              "name": "Дмитрий Иртеров",
              "email": "fatnsu.ru",
              "tgNickname": "@fat-Brother",
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
        // Только буквы!
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