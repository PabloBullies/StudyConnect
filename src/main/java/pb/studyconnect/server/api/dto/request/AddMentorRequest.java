package pb.studyconnect.server.api.dto.request;

import java.util.List;

public record AddMentorRequest(

        String name,

        String email,

        String tgNickname,

        List<String> scientificInterests,

        List<String> diplomaTopics,

        String department
) {

}
