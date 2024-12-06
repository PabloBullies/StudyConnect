package pb.studyconnect.server.util.mapper;

import org.mapstruct.Mapper;
import pb.studyconnect.server.api.dto.request.DiplomaTopicRequest;
import pb.studyconnect.server.api.dto.response.DiplomaTopicResponse;
import pb.studyconnect.server.model.DiplomaTopic;

@Mapper
public interface DiplomaTopicMapper {

    DiplomaTopic mapToDiplomaTopic(DiplomaTopicRequest request);

    DiplomaTopicResponse mapToDiplomaTopicResponse(DiplomaTopic diplomaTopic);
}
