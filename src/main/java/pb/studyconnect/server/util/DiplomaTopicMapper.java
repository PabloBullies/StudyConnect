package pb.studyconnect.server.util;

import org.mapstruct.Mapper;
import pb.studyconnect.server.api.dto.request.AddDiplomaTopicRequest;
import pb.studyconnect.server.api.dto.response.AddDiplomaTopicResponse;
import pb.studyconnect.server.model.DiplomaTopic;

@Mapper
public interface DiplomaTopicMapper {

    DiplomaTopic mapToDiplomaTopic(AddDiplomaTopicRequest request);

    AddDiplomaTopicResponse mapToDiplomaTopicResponse(DiplomaTopic diplomaTopic);
}
