package pb.studyconnect.server.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pb.studyconnect.server.api.dto.request.MentorRequest;
import pb.studyconnect.server.api.dto.response.DiplomaTopicResponse;
import pb.studyconnect.server.api.dto.response.MentorResponse;
import pb.studyconnect.server.api.dto.response.MentorResponseWithIsApprove;
import pb.studyconnect.server.api.dto.response.StudentResponseWithIsApprove;
import pb.studyconnect.server.model.Mentor;
import pb.studyconnect.server.model.Student;

import java.util.List;

@Mapper
public interface MentorMapper {

    Mentor mapToMentor(MentorRequest request);

    @Mapping(target = "diplomaTopics", source = "diplomaTopics")
    MentorResponse mapToMentorResponse(Mentor mentor, List<DiplomaTopicResponse> diplomaTopics);

    @Mapping(target = "isApprove", source = "isApprove")
    @Mapping(target = "diplomaTopics", source = "diplomaTopics")
    MentorResponseWithIsApprove mapToMentorResponseWithIsApprove(
            Mentor mentor,
            List<DiplomaTopicResponse> diplomaTopics,
            Boolean isApprove
    );

}
