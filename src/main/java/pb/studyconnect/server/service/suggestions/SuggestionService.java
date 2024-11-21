package pb.studyconnect.server.service.suggestions;

import pb.studyconnect.server.api.dto.response.MentorResponse;

public interface SuggestionService {
    MentorResponse suggestMentor(String studentId, Integer skip);
}
