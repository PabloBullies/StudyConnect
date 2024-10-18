package pb.studyconnect.server.service.students;

import pb.studyconnect.server.api.dto.request.MentorRequest;
import pb.studyconnect.server.api.dto.request.StudentRequest;
import pb.studyconnect.server.api.dto.response.MentorResponse;
import pb.studyconnect.server.api.dto.response.StudentResponse;

public interface StudentService {

    StudentResponse create(StudentRequest studentRequest);

    StudentResponse edit(String studentId, StudentRequest studentRequest);

    StudentResponse get(String studentId);

    void delete(String studentId);
}
