package pb.studyconnect.server.util.mapper;

import org.mapstruct.Mapper;
import pb.studyconnect.server.api.dto.request.StudentRequest;
import pb.studyconnect.server.api.dto.response.StudentResponse;
import pb.studyconnect.server.model.Student;

@Mapper
public interface StudentMapper {

    Student mapToStudent(StudentRequest request);

    StudentResponse mapToStudentResponse(Student student);
}
