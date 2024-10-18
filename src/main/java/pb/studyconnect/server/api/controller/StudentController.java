package pb.studyconnect.server.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pb.studyconnect.server.api.dto.request.MentorRequest;
import pb.studyconnect.server.api.dto.request.StudentRequest;
import pb.studyconnect.server.api.dto.response.MentorResponse;
import pb.studyconnect.server.api.dto.response.StudentResponse;
import pb.studyconnect.server.service.students.StudentService;

import static pb.studyconnect.server.api.path.ApiPaths.MENTORS;
import static pb.studyconnect.server.api.path.ApiPaths.PROFILES;
import static pb.studyconnect.server.api.path.ApiPaths.STUDENTS;

@RestController
@RequestMapping(PROFILES)
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping(STUDENTS)
    public StudentResponse create(@RequestBody @Valid StudentRequest studentRequest) {
        return studentService.create(studentRequest);
    }

    @PutMapping(STUDENTS + "/{id}")
    public StudentResponse edit(@PathVariable String id, @RequestBody @Valid StudentRequest studentRequest) {
        return studentService.edit(id, studentRequest);
    }

    @GetMapping(STUDENTS + "/{id}")
    public StudentResponse get(@PathVariable String id) {
        return studentService.get(id);
    }

    @DeleteMapping(STUDENTS + "/{id}")
    public void delete(@PathVariable String id) {
        studentService.delete(id);
    }

}
