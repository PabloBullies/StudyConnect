package pb.studyconnect.server.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("matches")
public class Match {

    @Id
    String id;

    String studentId;

    String mentorId;

    Boolean isApprove;
}
