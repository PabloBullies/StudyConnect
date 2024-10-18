package pb.studyconnect.server.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pb.studyconnect.server.api.dto.request.AddStudentRequest;
import pb.studyconnect.server.api.dto.response.AddStudentResponse;
import pb.studyconnect.server.service.students.StudentService;

import static pb.studyconnect.server.api.path.ApiPaths.PROFILES;
import static pb.studyconnect.server.api.path.ApiPaths.STUDENTS;

@RestController
@RequestMapping(PROFILES)
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping(STUDENTS)
    public AddStudentResponse create(@RequestBody @Valid AddStudentRequest addStudentRequest) {
        return studentService.create(addStudentRequest);
    }

}
