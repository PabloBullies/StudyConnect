package pb.studyconnect.server.service.matchings;

import pb.studyconnect.server.api.dto.response.MentorResponse;
import pb.studyconnect.server.api.dto.response.MentorResponseWithIsApprove;
import pb.studyconnect.server.api.dto.response.StudentResponse;
import pb.studyconnect.server.api.dto.response.StudentResponseWithIsApprove;

import java.util.List;

public interface MatchingService {

    void matchMentor(String studentId, String mentorId);

    void matchStudent(String studentId, String mentorId, Boolean isApprove);

    List<MentorResponseWithIsApprove> getMatchingMentors(String studentId);

    List<StudentResponseWithIsApprove> getMatchingStudents(String mentorId);
}
