package pb.studyconnect.server.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pb.studyconnect.server.api.dto.request.StudentRequest;
import pb.studyconnect.server.api.dto.response.StudentResponse;
import pb.studyconnect.server.api.dto.response.StudentResponseWithIsApprove;
import pb.studyconnect.server.model.Student;

@Mapper
public interface StudentMapper {

    Student mapToStudent(StudentRequest request);

    StudentResponse mapToStudentResponse(Student student);

    @Mapping(target = "isApprove", source = "isApprove")
    StudentResponseWithIsApprove mapToStudentResponseWithIsApprove(Student student, Boolean isApprove);
}
