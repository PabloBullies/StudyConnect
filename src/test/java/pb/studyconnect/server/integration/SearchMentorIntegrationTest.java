package pb.studyconnect.server.integration;

import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class SearchMentorIntegrationTest {
    @Test
    public void searchMentor() {
        Response response = given().header("Content-Type", "application/json")
                .and()
                .body("""
                        {
                          "name": "Абоба Сигмович",
                          "email": "aboba@gmail.ru",
                          "tgNickname": "@sigma",
                          "scientificInterests": ["CDM-16", "LLM","ОСИ"],
                          "skills": ["Assembler","vim","linux"],
                          "department": "кафедра систем информатики"
                        }
                        """)
                .when()
                .post("/profiles/students")
                .then()
                .extract().response();

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertNotEquals(null, response.jsonPath().getString("id"));
        Assertions.assertEquals("Абоба Сигмович", response.jsonPath().getString("name"));
        String id = response.jsonPath().getString("id");

        response = given().header("Content-Type", "application/json")
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
                                "neededSkills": ["стрессоустойчивость", "vim","linux"],
                                "scientificField": "системы оркестрации контейнеров"
                          }]
                        }
                        """)
                .when()
                .post("/profiles/mentors")
                .then()
                .extract().response();
        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertNotEquals(null, response.jsonPath().getString("id"));
        String mentor_id = response.jsonPath().getString("id");

        response = given().header("Content-Type", "application/json")
                .when()
                .get("/suggest/mentors/" + id)
                .then()
                .extract().response();

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertNotEquals(mentor_id, response.jsonPath().getString("id"));
        Assertions.assertNotEquals(null, response.jsonPath().getString("name"));
        Assertions.assertNotEquals(null, response.jsonPath().getString("scientificInterests"));
        Assertions.assertNotEquals(null, response.jsonPath().getString("diplomaTopics"));
        Assertions.assertNotEquals(null, response.jsonPath().getString("department"));
    }
}
