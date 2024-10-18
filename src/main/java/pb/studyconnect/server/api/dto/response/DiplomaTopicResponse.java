package pb.studyconnect.server.api.dto.response;

import java.util.List;

public record DiplomaTopicResponse(
    String id,

    String name,

    String summary,

    List<String>neededSkills,

    String scientificField
) {
}
