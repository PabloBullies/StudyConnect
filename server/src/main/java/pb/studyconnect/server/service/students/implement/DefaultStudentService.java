package pb.studyconnect.server.service.students.implement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pb.studyconnect.server.api.dto.request.AddStudentRequest;
import pb.studyconnect.server.api.dto.response.AddStudentResponse;
import pb.studyconnect.server.model.Student;
import pb.studyconnect.server.repository.StudentRepository;
import pb.studyconnect.server.service.students.StudentService;
import pb.studyconnect.server.util.Mapper;

@Service
@RequiredArgsConstructor
public class DefaultStudentService implements StudentService {

    private final StudentRepository studentRepository;


    @Override
    public AddStudentResponse create(AddStudentRequest addStudentRequest) {
        Student student = Mapper.mapToStudent(addStudentRequest);
        studentRepository.save(student);
        return Mapper.mapToAddStudentResponse(student);
    }
}
