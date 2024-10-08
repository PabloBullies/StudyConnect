package pb.studyconnect.server.api.dto.request;

import java.util.List;

public record AddDiplomaTopicsWithMentorRequest(
        String mentorId,

        List<AddDiplomaTopicRequest> diplomaTopics
) {
}
