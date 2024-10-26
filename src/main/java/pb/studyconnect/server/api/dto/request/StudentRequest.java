package pb.studyconnect.server.api.dto.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

import static pb.studyconnect.server.util.Messages.EMAIL_IS_NOT_IN_FORMAT;
import static pb.studyconnect.server.util.Messages.NAME_MUST_NOT_BE_EMPTY;
import static pb.studyconnect.server.util.Messages.SCIENTIFIC_INTERESTS_MINIMUM_LENGTH;
import static pb.studyconnect.server.util.Messages.TG_NICKNAME_IS_NOT_IN_FORMAT;
import static pb.studyconnect.server.util.Messages.TG_NICKNAME_MINIMUM_LENGTH;

public record StudentRequest(

        @NotBlank(message = NAME_MUST_NOT_BE_EMPTY)
        String name,

        @Pattern(regexp = "^[A-Za-z0-9.]+@[A-Za-z0-9.]+\\.[A-Za-z0-9.]+$", message = EMAIL_IS_NOT_IN_FORMAT)
        String email,

        @Pattern(regexp = "^@[A-Za-z0-9_]+$", message = TG_NICKNAME_IS_NOT_IN_FORMAT)
        @Size(min = 6, message = TG_NICKNAME_MINIMUM_LENGTH)
        String tgNickname,

        @Size(min = 3, message = SCIENTIFIC_INTERESTS_MINIMUM_LENGTH)
        List<String> scientificInterests,

        List<String> skills,

        @Nullable
        String department,

        @Nullable
        String initiativeTheme
) {

}
