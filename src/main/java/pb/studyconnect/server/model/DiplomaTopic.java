package pb.studyconnect.server.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("diploma_topics")
public class DiplomaTopic {

    @Id
    String id;

    String name;

    String summary;

    String neededSkills;

    String scientificField;
}
