package pb.studyconnect.server.unit.stub;

import pb.studyconnect.server.api.dto.request.MentorRequest;
import pb.studyconnect.server.api.dto.response.MentorResponse;
import pb.studyconnect.server.model.Mentor;
import pb.studyconnect.server.util.mapper.MentorMapper;

import java.util.List;

public class MentorStub {

    public static MentorRequest getBaseMentorRequest() {
        return new MentorRequest(
                "Pyvel",
                "kakashka@g.nsu.ru",
                "@piska",
                List.of("Sanya", "yoga", "python"),
                "hell",
                List.of(DiplomaTopicStub.getBaseDiplomaTopicRequest(), DiplomaTopicStub.getBaseDiplomaTopicRequest())
        );
    }

    public static MentorResponse getBaseMentorResponse() {
        return new MentorResponse(
                null,
                "Pyvel",
                "kakashka@g.nsu.ru",
                "@piska",
                List.of("Sanya", "yoga", "python"),
                List.of(
                        DiplomaTopicStub.getBaseDiplomaTopicResponse(),
                        DiplomaTopicStub.getBaseDiplomaTopicResponse()
                ),
                "hell"
        );
    }
}
