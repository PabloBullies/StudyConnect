package pb.studyconnect.server.service.students.implement;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pb.studyconnect.server.api.dto.request.StudentRequest;
import pb.studyconnect.server.api.dto.response.StudentResponse;
import pb.studyconnect.server.exception.PabloBullersException;
import pb.studyconnect.server.model.Student;
import pb.studyconnect.server.repository.StudentRepository;
import pb.studyconnect.server.service.students.StudentService;
import pb.studyconnect.server.util.mapper.StudentMapper;

@Service
@RequiredArgsConstructor
public class DefaultStudentService implements StudentService {

    private final StudentMapper studentMapper;

    private final StudentRepository studentRepository;

    @Override
    public StudentResponse create(StudentRequest studentRequest) {
        Student student = studentMapper.mapToStudent(studentRequest);
        studentRepository.save(student);
        return studentMapper.mapToStudentResponse(student);
    }

    @Override
    public StudentResponse edit(String studentId, StudentRequest studentRequest) {
        var student = studentRepository.findById(studentId)
                .orElseThrow(
                        () -> new PabloBullersException(
                                HttpStatus.NOT_FOUND.value(),
                                "Not found student with id: '" + studentId + "'"
                        )
                );

        student.setName(studentRequest.name());
        student.setEmail(studentRequest.email());
        student.setTgNickname(studentRequest.tgNickname());
        student.setScientificInterests(studentRequest.scientificInterests());
        student.setSkills(studentRequest.skills());
        student.setDepartment(studentRequest.department());
        student.setInitiativeTheme(studentRequest.initiativeTheme());
        studentRepository.save(student);
        return studentMapper.mapToStudentResponse(student);
    }

    @Override
    public StudentResponse get(String studentId) {
        var student = studentRepository.findById(studentId)
                .orElseThrow(
                        () -> new PabloBullersException(
                                HttpStatus.NOT_FOUND.value(),
                                "Not found student with id: '" + studentId + "'"
                        )
                );
        return studentMapper.mapToStudentResponse(student);
    }

    @Override
    public void delete(String studentId) {
        var student = studentRepository.findById(studentId)
                .orElseThrow(
                        () -> new PabloBullersException(
                                HttpStatus.NOT_FOUND.value(),
                                "Not found student with id: '" + studentId + "'"
                        )
                );
        studentRepository.delete(student);
    }
}
