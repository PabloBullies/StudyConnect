package pb.studyconnect.server.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pb.studyconnect.server.api.dto.request.StudentRequest;
import pb.studyconnect.server.api.dto.response.StudentResponse;
import pb.studyconnect.server.exception.PabloBullersException;
import pb.studyconnect.server.repository.StudentRepository;
import pb.studyconnect.server.service.students.StudentService;
import pb.studyconnect.server.unit.stub.StudentStub;
import pb.studyconnect.server.util.mapper.StudentMapper;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static pb.studyconnect.server.util.Messages.NOT_FOUND_STUDENT_WITH_ID;

import java.text.MessageFormat;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class StudentServiceTest {

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentMapper studentMapper;

    @MockBean
    private StudentRepository studentRepository;

    private final String studentId = "aboba";

    @Test
    void studentCreateWithSuccessTest() {
        StudentRequest studentRequest = StudentStub.getBaseStudentRequest();
        var actual = studentService.create(studentRequest);
        var expected = StudentStub.getBaseStudentResponse();
        assertThat(actual).isEqualTo(expected);
        Mockito.verify(studentRepository).save(studentMapper.mapToStudent(studentRequest));
    }

    @Test
    void studentEditWithSuccessTest() {
        StudentRequest studentRequest = StudentStub.getBaseStudentRequest();
        Mockito.when(studentRepository.findById(studentId))
                .thenReturn(Optional.of(studentMapper.mapToStudent(studentRequest)));
        var actual = studentService.edit(studentId, studentRequest);
        var expected = StudentStub.getBaseStudentResponse();
        assertThat(actual).isEqualTo(expected);
        Mockito.verify(studentRepository).save(studentMapper.mapToStudent(studentRequest));
    }

    @Test
    void studentEditWithNotFoundErrorTest() {
        StudentRequest studentRequest = StudentStub.getBaseStudentRequest();
        Mockito.when(studentRepository.findById(studentId)).thenReturn(Optional.empty());
        assertThatExceptionOfType(PabloBullersException.class)
                .isThrownBy(() -> studentService.edit(studentId, studentRequest))
                .matches(
                        actual -> actual.getCode().equals(HttpStatus.NOT_FOUND)
                                && actual.getMessage().equals(
                                MessageFormat.format(NOT_FOUND_STUDENT_WITH_ID, studentId)
                        )
                );
    }

    @Test
    void studentGetWithSuccessTest() {
        StudentRequest studentRequest = StudentStub.getBaseStudentRequest();
        Mockito.when(studentRepository.findById(studentId))
                .thenReturn(Optional.of(studentMapper.mapToStudent(studentRequest)));
        StudentResponse resp = studentService.get(studentId);
        Assertions.assertEquals(studentMapper.mapToStudentResponse(studentMapper.mapToStudent(studentRequest)), resp);
    }

    @Test
    void studentGetWithNotFoundErrorTest() {
        Mockito.when(studentRepository.findById(studentId)).thenReturn(Optional.empty());
        assertThatExceptionOfType(PabloBullersException.class)
                .isThrownBy(() -> studentService.get(studentId))
                .matches(
                        actual -> actual.getCode().equals(HttpStatus.NOT_FOUND)
                                && actual.getMessage().equals(
                                MessageFormat.format(NOT_FOUND_STUDENT_WITH_ID, studentId)
                        )
                );
    }

    @Test
    void studentDeleteWithSuccessTest() {
        StudentRequest studentRequest = StudentStub.getBaseStudentRequest();
        Mockito.when(studentRepository.findById(studentId))
                .thenReturn(Optional.of(studentMapper.mapToStudent(studentRequest)));
        studentService.delete(studentId);
        Mockito.verify(studentRepository).delete(studentMapper.mapToStudent(studentRequest));
    }

    @Test
    void studentDeleteWithNotFoundErrorTest() {
        Mockito.when(studentRepository.findById(studentId)).thenReturn(Optional.empty());
        assertThatExceptionOfType(PabloBullersException.class)
                .isThrownBy(() -> studentService.delete(studentId))
                .matches(
                        actual -> actual.getCode().equals(HttpStatus.NOT_FOUND)
                                && actual.getMessage().equals(
                                MessageFormat.format(NOT_FOUND_STUDENT_WITH_ID, studentId)
                        )
                );
    }
}
