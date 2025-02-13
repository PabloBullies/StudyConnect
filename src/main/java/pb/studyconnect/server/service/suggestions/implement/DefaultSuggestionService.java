package pb.studyconnect.server.service.suggestions.implement;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pb.studyconnect.server.api.dto.response.MentorResponse;
import pb.studyconnect.server.exception.PabloBullersException;
import pb.studyconnect.server.model.Mentor;
import pb.studyconnect.server.repository.DiplomaTopicRepository;
import pb.studyconnect.server.repository.StudentRepository;
import pb.studyconnect.server.service.suggestions.SuggestionService;
import pb.studyconnect.server.util.mapper.DiplomaTopicMapper;
import pb.studyconnect.server.util.mapper.MentorMapper;

import java.text.MessageFormat;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.limit;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.skip;
import static pb.studyconnect.server.util.Messages.NOT_FOUND_STUDENT_WITH_ID;

@Log4j2
@Service
@RequiredArgsConstructor
public class DefaultSuggestionService implements SuggestionService {

    private final StudentRepository studentRepository;

    private final DiplomaTopicRepository diplomaTopicRepository;

    private final MentorMapper mentorMapper;

    private final DiplomaTopicMapper diplomaTopicMapper;

    private final MongoTemplate mongoTemplate;

    private static final Marker marker = MarkerManager.getMarker("SUGGESTION SERVICE");

    @Override
    public MentorResponse suggestMentor(String studentId, Integer skip) {
        var student = studentRepository.findById(studentId)
                .orElseThrow(
                        () -> new PabloBullersException(
                                HttpStatus.NOT_FOUND,
                                MessageFormat.format(NOT_FOUND_STUDENT_WITH_ID, studentId)
                        )
                );

        Aggregation diplomaTopicAggregation = newAggregation(
                match(Criteria.where("neededSkills").in(student.getSkills())),
                project("_id")
        );

        AggregationResults<Document> topicResults = mongoTemplate.aggregate(
                diplomaTopicAggregation,
                "diploma_topics",
                Document.class
        );
        List<String> diplomaTopicIds = topicResults.getMappedResults()
                .stream()
                .map(doc -> doc.get("_id").toString())
                .toList();

        if (diplomaTopicIds.isEmpty()) {
            log.info(marker, "No suitable diploma topics found");
            return null;
        }

        Aggregation mentorAggregation = newAggregation(
                match(Criteria.where("diplomaTopicIds").in(diplomaTopicIds)),
                skip(skip),
                limit(1)
        );

        AggregationResults<Mentor> mentorResults = mongoTemplate.aggregate(
                mentorAggregation,
                "mentors",
                Mentor.class
        );

        if (mentorResults.getMappedResults().isEmpty()) {
            log.info(marker, "No suitable mentors found");
            return null;
        }

        var mentor = mentorResults.getMappedResults().getFirst();

        var diplomaTopics = diplomaTopicRepository.findAllById(mentor.getDiplomaTopicIds());

        log.info(marker, "Suggest mentor named {} for student named {}", mentor.getName(), student.getName());

        return mentorMapper.mapToMentorResponse(
                mentor,
                diplomaTopics.stream().map(diplomaTopicMapper::mapToDiplomaTopicResponse).toList()
        );
    }
}
