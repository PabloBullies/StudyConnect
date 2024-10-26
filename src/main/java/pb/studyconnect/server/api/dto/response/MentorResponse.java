package pb.studyconnect.server.api.dto.response;

import org.springframework.data.mongodb.core.mapping.DBRef;
import pb.studyconnect.server.model.DiplomaTopic;

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
