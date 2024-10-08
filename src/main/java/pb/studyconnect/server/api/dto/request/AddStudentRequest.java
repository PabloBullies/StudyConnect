package pb.studyconnect.server.api.dto.request;

import java.util.List;

public record AddStudentRequest(

        String name,

        String email,

        String tgNickname,

        List<String> scientificInterests,

        List<String> skills,

        String department,

        String initiativeTheme
) {

}
