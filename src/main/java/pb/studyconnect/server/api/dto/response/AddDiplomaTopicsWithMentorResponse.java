package pb.studyconnect.server.api.dto.response;

import java.util.List;

public record AddDiplomaTopicsWithMentorResponse(
        String mentorId,

        List<AddDiplomaTopicResponse> diplomaTopics
) {
}
