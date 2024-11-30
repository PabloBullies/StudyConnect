package pb.studyconnect.server.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pb.studyconnect.server.api.dto.request.StudentRequest;
import pb.studyconnect.server.api.dto.response.StudentResponse;
import pb.studyconnect.server.repository.StudentRepository;
import pb.studyconnect.server.service.students.StudentService;
import pb.studyconnect.server.unit.stub.StudentRequestStub;
import pb.studyconnect.server.util.mapper.StudentMapper;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class StudentServiceTest {

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentMapper studentMapper;

    @MockBean
    private StudentRepository studentRepository;


    @Test
    public void studentCreateTest() {
        StudentRequest studentRequest = StudentRequestStub.getBaseStudent();
        studentService.create(studentRequest);
        Mockito.verify(studentRepository, Mockito.times(1)).save(studentMapper.mapToStudent(studentRequest));
    }

    @Test
    public void studentEditTest() {
        StudentRequest studentRequest = StudentRequestStub.getBaseStudent();
        Mockito.when(studentRepository.findById("aboba")).thenReturn(Optional.of(studentMapper.mapToStudent(studentRequest)));
        studentService.edit("aboba", studentRequest);
        Mockito.verify(studentRepository, Mockito.times(1)).save(studentMapper.mapToStudent(studentRequest));
    }

    @Test
    public void studentGetTest() {
        StudentRequest studentRequest = StudentRequestStub.getBaseStudent();
        Mockito.when(studentRepository.findById("aboba")).thenReturn(Optional.of(studentMapper.mapToStudent(studentRequest)));
        StudentResponse resp = studentService.get("aboba");
        Assertions.assertEquals(studentMapper.mapToStudentResponse(studentMapper.mapToStudent(studentRequest)), resp);
    }

    @Test
    public void studentDeleteTest() {
        StudentRequest studentRequest = StudentRequestStub.getBaseStudent();
        Mockito.when(studentRepository.findById("aboba")).thenReturn(Optional.of(studentMapper.mapToStudent(studentRequest)));
        studentService.delete("aboba");
        Mockito.verify(studentRepository, Mockito.times(1)).delete(studentMapper.mapToStudent(studentRequest));
    }
}
