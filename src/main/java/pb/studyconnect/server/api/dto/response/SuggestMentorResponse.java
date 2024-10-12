package pb.studyconnect.server.api.dto.response;

import pb.studyconnect.server.model.DiplomaTopic;

import java.util.List;

public record SuggestMentorResponse(
        String id,
        String name,
        List<String>scientificInterests,
        List<DiplomaTopic> diplomaTopics,
        String department
)  {
}