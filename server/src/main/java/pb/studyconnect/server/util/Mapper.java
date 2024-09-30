package pb.studyconnect.server.util;

import pb.studyconnect.server.api.dto.request.AddMentorRequest;
import pb.studyconnect.server.api.dto.request.AddStudentRequest;
import pb.studyconnect.server.api.dto.response.AddMentorResponse;
import pb.studyconnect.server.api.dto.response.AddStudentResponse;
import pb.studyconnect.server.model.Mentor;
import pb.studyconnect.server.model.Student;

public class Mapper {

    public static Student mapToStudent(AddStudentRequest request) {
        return Student.builder()
                .name(request.getName())
                .email(request.getEmail())
                .tgNickname(request.getTgNickname())
                .scientificInterests(request.getScientificInterests())
                .skills(request.getSkills())
                .department(request.getDepartment())
                .initiativeTheme(request.getInitiativeTheme())
                .build();
    }

    public static AddStudentResponse mapToAddStudentResponse(Student student) {
        return AddStudentResponse.builder()
                .id(student.getId())
                .name(student.getName())
                .build();
    }

    public static Mentor mapToMentor(AddMentorRequest request) {
        return Mentor.builder()
                .name(request.getName())
                .email(request.getEmail())
                .tgNickname(request.getTgNickname())
                .scientificInterests(request.getScientificInterests())
                .diplomaTopics(request.getDiplomaTopics())
                .department(request.getDepartment())
                .build();
    }

    public static AddMentorResponse mapToAddMentorResponse(Mentor mentor) {
        return AddMentorResponse.builder()
                .id(mentor.getId())
                .name(mentor.getName())
                .build();
    }
}
