package pb.studyconnect.server.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pb.studyconnect.server.api.dto.request.DiplomaTopicRequest;
import pb.studyconnect.server.api.dto.response.DiplomaTopicResponse;
import pb.studyconnect.server.model.DiplomaTopic;

@Mapper
public interface DiplomaTopicMapper {

    DiplomaTopic mapToDiplomaTopic(DiplomaTopicRequest request, String mentorId);

    DiplomaTopicResponse mapToDiplomaTopicResponse(DiplomaTopic diplomaTopic);
}
