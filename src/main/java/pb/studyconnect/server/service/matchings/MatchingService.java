package pb.studyconnect.server.service.matchings;

public interface MatchingService {

    void matchMentor(String studentId, String mentorId);

    void matchStudent(String studentId, String mentorId, Boolean isApprove);
}
