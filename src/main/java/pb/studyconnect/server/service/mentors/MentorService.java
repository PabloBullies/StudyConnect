package pb.studyconnect.server.service.mentors;

import pb.studyconnect.server.api.dto.request.DiplomaTopicRequest;
import pb.studyconnect.server.api.dto.request.MentorRequest;
import pb.studyconnect.server.api.dto.response.DiplomaTopicResponse;
import pb.studyconnect.server.api.dto.response.MentorResponse;

import java.util.List;

public interface MentorService {

    MentorResponse create(MentorRequest mentorRequest);

    MentorResponse edit(String mentorId, MentorRequest mentorRequest);

    MentorResponse get(String mentorId);

    void delete(String mentorId);

}
