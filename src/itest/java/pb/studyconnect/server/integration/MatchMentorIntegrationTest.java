package pb.studyconnect.server.integration;

import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class MatchMentorIntegrationTest extends BaseIntegrationTest {
    @Test
    public void matchStudentByMentor() {
        // Preparing data
        Response response = given().header("Content-Type", "application/json")
                .and()
                .body("""
                        {
                          "name": "Абоба Сигмович",
                          "email": "aboba@gmail.ru",
                          "tgNickname": "@sigma007",
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
        Assertions.assertNotEquals(null, response.jsonPath().getString("id"));
        String studentId = response.jsonPath().getString("id");

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
        String mentorId = response.jsonPath().getString("id");

        // BeforeMatch
        // Check not exists
        response = given().header("Content-Type", "application/json")
                .when()
                .get("/matches/" + studentId + "/mentors")
                .then()
                .extract().response();
        Assertions.assertEquals(200, response.statusCode());
        List<String> ids = response.jsonPath().getList("id");
        Assertions.assertFalse(ids.contains(mentorId));

        // Check not exists
        response = given().header("Content-Type", "application/json")
                .when()
                .get("/matches/" + mentorId + "/students")
                .then()
                .extract().response();
        Assertions.assertEquals(200, response.statusCode());
        ids = response.jsonPath().getList("id");
        Assertions.assertFalse(ids.contains(studentId));

        // Match
        response = given().header("Content-Type", "application/json")
                .when()
                .post("/matches/" + studentId + "/mentors/" + mentorId)
                .then()
                .extract().response();
        Assertions.assertEquals(200, response.statusCode());

        // Check matches by student
        // Check not approved
        response = given().header("Content-Type", "application/json")
                .when()
                .get("/matches/" + studentId + "/mentors")
                .then()
                .extract().response();
        Assertions.assertEquals(200, response.statusCode());
        List<Map<String, Object>> mentors = response.jsonPath().getList("$");
        boolean isApproved = mentors.stream().anyMatch(item -> item.get("id").equals(mentorId) && item.get("isApprove").equals(true));
        Assertions.assertFalse(isApproved);

        // Check not approved
        response = given().header("Content-Type", "application/json")
                .when()
                .get("/matches/" + mentorId + "/students")
                .then()
                .extract().response();
        Assertions.assertEquals(200, response.statusCode());
        ids = response.jsonPath().getList("id");
        Assertions.assertTrue(ids.contains(studentId));
        List<Map<String, Object>> students = response.jsonPath().getList("$");
        isApproved = students.stream().anyMatch(item -> item.get("id").equals(studentId) && item.get("isApprove").equals(true));
        Assertions.assertFalse(isApproved);

        // Approve
        response = given().header("Content-Type", "application/json")
                .when()
                .post("/matches/" + mentorId + "/students/" + studentId + "/?isApprove=true")
                .then()
                .extract().response();
        Assertions.assertEquals(200, response.statusCode());

        // Check not approved
        response = given().header("Content-Type", "application/json")
                .when()
                .get("/matches/" + studentId + "/mentors")
                .then()
                .extract().response();
        Assertions.assertEquals(200, response.statusCode());
        mentors = response.jsonPath().getList("$");
        isApproved = mentors.stream().anyMatch(item -> item.get("id").equals(mentorId) && item.get("isApprove").equals(true));
        Assertions.assertTrue(isApproved);

        // Check not approved
        response = given().header("Content-Type", "application/json")
                .when()
                .get("/matches/" + mentorId + "/students")
                .then()
                .extract().response();
        Assertions.assertEquals(200, response.statusCode());
        ids = response.jsonPath().getList("id");
        Assertions.assertTrue(ids.contains(studentId));
        students = response.jsonPath().getList("$");
        isApproved = students.stream().anyMatch(item -> item.get("id").equals(studentId) && item.get("isApprove").equals(true));
        Assertions.assertTrue(isApproved);
    }
}
