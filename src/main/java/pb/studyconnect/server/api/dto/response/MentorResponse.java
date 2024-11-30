package pb.studyconnect.server.api.dto.response;

import java.util.List;

public record MentorResponse(
    String id,

    String name,

    String email,

    String tgNickname,

    List<String>scientificInterests,

    List<DiplomaTopicResponse> diplomaTopics,

    String department
) {
}
