package pb.studyconnect.server.model;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document("mentors")
public class Mentor {

    @Id
    String id;

    String name;

    String email;

    String tgNickname;

    List<String> scientificInterests;

    @DBRef
    List<DiplomaTopic> diplomaTopics;

    String department;
}
