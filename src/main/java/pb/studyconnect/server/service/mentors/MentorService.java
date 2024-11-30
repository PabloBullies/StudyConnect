package pb.studyconnect.server.service.mentors;

import pb.studyconnect.server.api.dto.request.MentorRequest;
import pb.studyconnect.server.api.dto.response.MentorResponse;

public interface MentorService {

    MentorResponse create(MentorRequest mentorRequest);

    MentorResponse edit(String mentorId, MentorRequest mentorRequest);

    MentorResponse get(String mentorId);

    void delete(String mentorId);

}
