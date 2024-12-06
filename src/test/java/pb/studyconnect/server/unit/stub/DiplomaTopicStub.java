package pb.studyconnect.server.unit.stub;

import pb.studyconnect.server.api.dto.request.DiplomaTopicRequest;
import pb.studyconnect.server.api.dto.response.DiplomaTopicResponse;

import java.util.List;

public class DiplomaTopicStub {

    public static DiplomaTopicRequest getBaseDiplomaTopicRequest() {
        return new DiplomaTopicRequest(
                "diploma",
                "to do something",
                List.of("бегит", "пресс качат", "отжуманя"),
                "sport"
        );
    }

    public static DiplomaTopicResponse getBaseDiplomaTopicResponse() {
        return new DiplomaTopicResponse(
                null,
                "diploma",
                "to do something",
                List.of("бегит", "пресс качат", "отжуманя"),
                "sport"
        );
    }
}
