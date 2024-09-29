package pb.studyconnect.server.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("students")
public class Student {

    @Id
    String id;

    String name;

    String email;

    String tgNickname;

    List<String> scientificInterests;

    List<String> skills;

    String department;

    String initiativeTheme;

}
