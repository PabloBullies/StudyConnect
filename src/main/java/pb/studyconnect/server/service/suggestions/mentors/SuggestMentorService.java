package pb.studyconnect.server.service.suggestions.mentors;

import pb.studyconnect.server.api.dto.response.MentorResponse;
import pb.studyconnect.server.api.dto.response.SuggestMentorResponse;

public interface SuggestMentorService {
    MentorResponse suggest(String studentId, Integer offset);
}
