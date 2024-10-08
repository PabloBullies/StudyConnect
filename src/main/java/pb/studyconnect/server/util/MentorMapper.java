package pb.studyconnect.server.util;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pb.studyconnect.server.api.dto.request.AddMentorRequest;
import pb.studyconnect.server.api.dto.response.AddMentorResponse;
import pb.studyconnect.server.model.Mentor;

@Mapper
public interface MentorMapper {

    Mentor mapToMentor(AddMentorRequest request);

    AddMentorResponse mapToAddMentorResponse(Mentor mentor);
}
