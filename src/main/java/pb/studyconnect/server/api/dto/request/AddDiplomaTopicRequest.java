package pb.studyconnect.server.api.dto.request;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

import static pb.studyconnect.server.util.Messages.NAME_MUST_NOT_BE_EMPTY;

public record AddDiplomaTopicRequest(

        @NotBlank(message = NAME_MUST_NOT_BE_EMPTY)
        String name,

        String summary,

        List<String> neededSkills,

        String scientificField

) {
}
