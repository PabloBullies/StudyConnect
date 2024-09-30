package pb.studyconnect.server.service.mentors;

import pb.studyconnect.server.api.dto.request.AddMentorRequest;
import pb.studyconnect.server.api.dto.response.AddMentorResponse;

public interface MentorService {

    AddMentorResponse create(AddMentorRequest addMentorRequest);
}
