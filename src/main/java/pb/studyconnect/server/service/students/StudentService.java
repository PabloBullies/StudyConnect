package pb.studyconnect.server.service.students;

import pb.studyconnect.server.api.dto.request.AddStudentRequest;
import pb.studyconnect.server.api.dto.response.AddStudentResponse;

public interface StudentService {

    AddStudentResponse create(AddStudentRequest addStudentRequest);
}
