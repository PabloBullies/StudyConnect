package pb.studyconnect.server.api.dto.request;

import lombok.Value;

import java.util.List;

@Value
public class AddMentorRequest {

    String name;

    String email;

    String tgNickname;

    List<String> scientificInterests;

    List<String> diplomaTopics;

    String department;
}
