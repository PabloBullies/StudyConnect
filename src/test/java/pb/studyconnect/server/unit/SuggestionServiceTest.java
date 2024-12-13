package pb.studyconnect.server.unit;

import org.bson.Document;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pb.studyconnect.server.api.dto.request.MentorRequest;
import pb.studyconnect.server.api.dto.request.StudentRequest;
import pb.studyconnect.server.exception.PabloBullersException;
import pb.studyconnect.server.model.Mentor;
import pb.studyconnect.server.repository.DiplomaTopicRepository;
import pb.studyconnect.server.repository.StudentRepository;
import pb.studyconnect.server.service.suggestions.implement.DefaultSuggestionService;
import pb.studyconnect.server.unit.stub.MentorStub;
import pb.studyconnect.server.unit.stub.StudentStub;
import pb.studyconnect.server.util.mapper.DiplomaTopicMapper;
import pb.studyconnect.server.util.mapper.DiplomaTopicMapperImpl;
import pb.studyconnect.server.util.mapper.MentorMapper;
import pb.studyconnect.server.util.mapper.MentorMapperImpl;
import pb.studyconnect.server.util.mapper.StudentMapper;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.argThat;
import static pb.studyconnect.server.util.Messages.NOT_FOUND_STUDENT_WITH_ID;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class SuggestionServiceTest {

    @Autowired
    private StudentMapper studentMapper;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private DiplomaTopicRepository diplomaTopicRepository;

    @Spy
    private MentorMapper mentorMapper = new MentorMapperImpl();

    @Spy
    private DiplomaTopicMapper diplomaTopicMapper = new DiplomaTopicMapperImpl();

    @Mock
    private MongoTemplate mongoTemplate;

    @InjectMocks
    private DefaultSuggestionService suggestionService;

    private final String studentId = "aboba";

    @Test
    void suggestMentorWithSuccessTest() {
        StudentRequest studentRequest = StudentStub.getBaseStudentRequest();
        Mockito.when(studentRepository.findById(studentId)).thenReturn(Optional.of(studentMapper.mapToStudent(studentRequest)));

        AggregationResults<Document> mockTopicResults = Mockito.mock(AggregationResults.class);
        Document doc1 = new Document("_id", "doc1");
        Document doc2 = new Document("_id", "doc2");
        Document doc3 = new Document("_id", "doc3");

        List<Document> docs = new ArrayList<>(List.of(doc1, doc2, doc3));

        Mockito.when(mongoTemplate.aggregate(
                (Aggregation) argThat(Objects::nonNull),
                (String) argThat(arg -> arg.equals("diploma_topics")),
                argThat(arg -> arg.equals(Document.class)))
        ).thenAnswer(invocationOnMock -> mockTopicResults);

        Mockito.when(mockTopicResults.getMappedResults()).thenReturn(docs);

        AggregationResults<Mentor> mockMentorResults = Mockito.mock(AggregationResults.class);

        Mockito.when(mongoTemplate.aggregate(
                (Aggregation) argThat(Objects::nonNull),
                (String) argThat(arg -> arg.equals("mentors")),
                argThat(arg -> arg.equals(Mentor.class)))
        ).thenAnswer(invocationOnMock -> mockMentorResults);

        MentorRequest mentorRequest = MentorStub.getBaseMentorRequest();
        var mentor = mentorMapper.mapToMentor(mentorRequest);
        mentor.setDiplomaTopicIds(List.of("doc1", "doc2"));

        Mockito.when(mockMentorResults.getMappedResults()).thenReturn(List.of(mentor));

        var diplomaTopics = mentorRequest.diplomaTopics()
                .stream()
                .map(diplomaTopicMapper::mapToDiplomaTopic)
                .toList();

        Mockito.when(diplomaTopicRepository.findAllById(mentor.getDiplomaTopicIds())).thenReturn(diplomaTopics);

        var actual = suggestionService.suggestMentor(studentId, 0);

        Mockito.verify(diplomaTopicRepository).findAllById(mentor.getDiplomaTopicIds());
        Assertions.assertEquals(MentorStub.getBaseMentorResponse(), actual);
    }

    @Test
    void suggestMentorWithStudentNotFoundTest() {
        Mockito.when(studentRepository.findById(studentId)).thenReturn(Optional.empty());
        assertThatExceptionOfType(PabloBullersException.class)
                .isThrownBy(() -> suggestionService.suggestMentor(studentId, 0))
                .matches(
                        actual -> actual.getCode().equals(HttpStatus.NOT_FOUND)
                                && actual.getMessage().equals(
                                MessageFormat.format(NOT_FOUND_STUDENT_WITH_ID, studentId)
                        )
                );
    }

}
