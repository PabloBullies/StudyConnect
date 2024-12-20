package pb.studyconnect.server.service.students.implement;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pb.studyconnect.server.api.dto.request.StudentRequest;
import pb.studyconnect.server.api.dto.response.StudentResponse;
import pb.studyconnect.server.exception.PabloBullersException;
import pb.studyconnect.server.model.Student;
import pb.studyconnect.server.repository.StudentRepository;
import pb.studyconnect.server.service.students.StudentService;
import pb.studyconnect.server.util.mapper.StudentMapper;

import java.text.MessageFormat;

import static pb.studyconnect.server.util.Messages.NOT_FOUND_STUDENT_WITH_ID;

@Log4j2
@Service
@RequiredArgsConstructor
public class DefaultStudentService implements StudentService {

    private final StudentMapper studentMapper;

    private final StudentRepository studentRepository;

    private static final Marker marker = MarkerManager.getMarker("STUDENT SERVICE");

    @Override
    public StudentResponse create(StudentRequest studentRequest) {
        Student student = studentMapper.mapToStudent(studentRequest);
        studentRepository.save(student);
        log.info(marker, "Student with name {} was created", student.getName());
        return studentMapper.mapToStudentResponse(student);
    }

    @Override
    public StudentResponse edit(String studentId, StudentRequest studentRequest) {
        var student = studentRepository.findById(studentId)
                .orElseThrow(
                        () -> new PabloBullersException(
                                HttpStatus.NOT_FOUND,
                                MessageFormat.format(NOT_FOUND_STUDENT_WITH_ID, studentId)
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
        log.info(marker, "Student with name {} was edited", student.getName());
        return studentMapper.mapToStudentResponse(student);
    }

    @Override
    public StudentResponse get(String studentId) {
        var student = studentRepository.findById(studentId)
                .orElseThrow(
                        () -> new PabloBullersException(
                                HttpStatus.NOT_FOUND,
                                MessageFormat.format(NOT_FOUND_STUDENT_WITH_ID, studentId)
                        )
                );
        log.info(marker, "Student with name {} was received", student.getName());
        return studentMapper.mapToStudentResponse(student);
    }

    @Override
    public void delete(String studentId) {
        var student = studentRepository.findById(studentId)
                .orElseThrow(
                        () -> new PabloBullersException(
                                HttpStatus.NOT_FOUND,
                                MessageFormat.format(NOT_FOUND_STUDENT_WITH_ID, studentId)
                        )
                );
        studentRepository.delete(student);
        log.info(marker, "Student with id {} was deleted", studentId);
    }
}
