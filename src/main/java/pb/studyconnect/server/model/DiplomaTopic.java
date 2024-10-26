package pb.studyconnect.server.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document("diploma_topics")
public class DiplomaTopic {

    @Id
    String id;

    String name;

    String summary;

    List<String> neededSkills;

    String scientificField;
}
