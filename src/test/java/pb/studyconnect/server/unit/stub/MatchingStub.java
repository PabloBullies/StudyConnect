package pb.studyconnect.server.unit.stub;

import pb.studyconnect.server.model.Matching;

public class MatchingStub {

    public static Matching getBaseMatching(String studentId, String mentorId, boolean isApprove) {
        Matching matching = new Matching();
        matching.setMentorId(mentorId);
        matching.setStudentId(studentId);
        matching.setIsApprove(isApprove);
        return matching;
    }
}
