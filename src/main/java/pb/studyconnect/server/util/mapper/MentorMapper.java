package pb.studyconnect.server.util.mapper;

import org.mapstruct.Mapper;
import pb.studyconnect.server.api.dto.request.MentorRequest;
import pb.studyconnect.server.api.dto.response.MentorResponse;
import pb.studyconnect.server.model.Mentor;

@Mapper
public interface MentorMapper {

    Mentor mapToMentor(MentorRequest request);

    MentorResponse mapToMentorResponse(Mentor mentor);
}
