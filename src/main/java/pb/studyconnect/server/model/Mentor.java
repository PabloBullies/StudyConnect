package pb.studyconnect.server.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Builder
@Data
@Document("mentors")
public class Mentor {

    @Id
    String id;

    String name;

    String email;

    String tgNickname;

    List<String> scientificInterests;

    List<String> diplomaTopics;

    String department;
}
