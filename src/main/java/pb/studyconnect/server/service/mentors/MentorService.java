package pb.studyconnect.server.service.mentors;

import pb.studyconnect.server.api.dto.request.AddDiplomaTopicRequest;
import pb.studyconnect.server.api.dto.request.AddMentorRequest;
import pb.studyconnect.server.api.dto.response.AddDiplomaTopicResponse;
import pb.studyconnect.server.api.dto.response.AddMentorResponse;

import java.util.List;

public interface MentorService {

    AddMentorResponse create(AddMentorRequest addMentorRequest);

    List<AddDiplomaTopicResponse> addDiplomaTopics(String mentorId, List<AddDiplomaTopicRequest> request);

}
