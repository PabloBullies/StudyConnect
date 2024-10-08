package pb.studyconnect.server.util;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pb.studyconnect.server.api.dto.request.AddStudentRequest;
import pb.studyconnect.server.api.dto.response.AddStudentResponse;
import pb.studyconnect.server.model.Student;

@Mapper
public interface StudentMapper {

    Student mapToStudent(AddStudentRequest request);

    AddStudentResponse mapToAddStudentResponse(Student student);
}
