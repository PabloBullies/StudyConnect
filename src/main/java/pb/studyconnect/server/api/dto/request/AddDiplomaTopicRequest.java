package pb.studyconnect.server.api.dto.request;

public record AddDiplomaTopicRequest(

        String name,

        String summary,

        String neededSkills,

        String scientificField

) {
}
