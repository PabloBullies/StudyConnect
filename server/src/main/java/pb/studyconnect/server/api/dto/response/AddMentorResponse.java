package pb.studyconnect.server.api.dto.response;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class AddMentorResponse {

    String id;

    String name;
}
