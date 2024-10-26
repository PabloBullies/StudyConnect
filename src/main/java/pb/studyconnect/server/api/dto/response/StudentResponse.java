package pb.studyconnect.server.api.dto.response;

import java.util.List;

public record StudentResponse(
    String id,

    String name,

    String email,

    String tgNickname,

    List<String> scientificInterests,

    List<String> skills,

    String department,

    String initiativeTheme
) {
}
