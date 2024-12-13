package pb.studyconnect.server.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pb.studyconnect.server.api.dto.request.MentorRequest;
import pb.studyconnect.server.api.dto.request.StudentRequest;
import pb.studyconnect.server.exception.PabloBullersException;
import pb.studyconnect.server.model.Matching;
import pb.studyconnect.server.repository.DiplomaTopicRepository;
import pb.studyconnect.server.repository.MatchingRepository;
import pb.studyconnect.server.repository.MentorRepository;
import pb.studyconnect.server.repository.StudentRepository;
import pb.studyconnect.server.service.matchings.implement.DefaultMatchingService;
import pb.studyconnect.server.unit.stub.MatchingStub;
import pb.studyconnect.server.unit.stub.MentorStub;
import pb.studyconnect.server.unit.stub.StudentStub;
import pb.studyconnect.server.util.mapper.DiplomaTopicMapper;
import pb.studyconnect.server.util.mapper.DiplomaTopicMapperImpl;
import pb.studyconnect.server.util.mapper.MentorMapper;
import pb.studyconnect.server.util.mapper.MentorMapperImpl;
import pb.studyconnect.server.util.mapper.StudentMapper;
import pb.studyconnect.server.util.mapper.StudentMapperImpl;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static pb.studyconnect.server.util.Messages.NOT_FOUND_MATCHING_WITH_STUDENT_ID_AND_MENTOR_ID;
import static pb.studyconnect.server.util.Messages.NOT_FOUND_MENTOR_WITH_ID;
import static pb.studyconnect.server.util.Messages.NOT_FOUND_STUDENT_WITH_ID;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class MatchingServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private MentorRepository mentorRepository;

    @Mock
    private MatchingRepository matchingRepository;

    @Mock
    private DiplomaTopicRepository diplomaTopicRepository;

    @Spy
    private MentorMapper mentorMapper = new MentorMapperImpl();

    @Spy
    private DiplomaTopicMapper diplomaTopicMapper = new DiplomaTopicMapperImpl();

    @Spy
    private StudentMapper studentMapper = new StudentMapperImpl();

    @InjectMocks
    private DefaultMatchingService matchingService;

    private final String studentId = "aboba1";
    private final String mentorId = "aboba2";

    @Test
    void matchMentorWithSuccess() {
        Mockito.when(studentRepository.existsById(studentId)).thenReturn(true);
        Mockito.when(mentorRepository.existsById(mentorId)).thenReturn(true);
        Matching matching = MatchingStub.getBaseMatching(studentId, mentorId, false);

        Mockito.when(matchingRepository.findFirstByMentorIdAndStudentId(mentorId, studentId))
                .thenReturn(Optional.empty());
        matchingService.matchMentor(studentId, mentorId);
        Mockito.verify(matchingRepository).save(matching);
    }

    @Test
    void matchMentorWithStudentNotFound() {
        Mockito.when(studentRepository.existsById(studentId)).thenReturn(false);
        assertThatExceptionOfType(PabloBullersException.class)
                .isThrownBy(() -> matchingService.matchMentor(studentId, mentorId))
                .matches(
                        actual -> actual.getCode().equals(HttpStatus.NOT_FOUND)
                                && actual.getMessage().equals(
                                        MessageFormat.format(NOT_FOUND_STUDENT_WITH_ID, studentId)
                        )
                );
    }

    @Test
    void matchMentorWithMentorNotFound() {
        Mockito.when(studentRepository.existsById(studentId)).thenReturn(true);
        Mockito.when(mentorRepository.existsById(mentorId)).thenReturn(false);
        assertThatExceptionOfType(PabloBullersException.class)
                .isThrownBy(() -> matchingService.matchMentor(studentId, mentorId))
                .matches(
                        actual -> actual.getCode().equals(HttpStatus.NOT_FOUND)
                                && actual.getMessage().equals(
                                        MessageFormat.format(NOT_FOUND_MENTOR_WITH_ID, mentorId)
                        )
                );
    }

    @Test
    void matchStudentWithSuccess() {
        Mockito.when(studentRepository.existsById(studentId)).thenReturn(true);
        Mockito.when(mentorRepository.existsById(mentorId)).thenReturn(true);
        Matching matching = MatchingStub.getBaseMatching(studentId, mentorId, true);

        Mockito.when(matchingRepository.findFirstByMentorIdAndStudentId(mentorId, studentId))
                .thenReturn(Optional.of(matching));

        matchingService.matchStudent(studentId, mentorId, true);
        Mockito.verify(matchingRepository).save(matching);
    }

    @Test
    void matchStudentWithStudentNotFound() {
        Mockito.when(studentRepository.existsById(studentId)).thenReturn(false);
        assertThatExceptionOfType(PabloBullersException.class)
                .isThrownBy(() -> matchingService.matchStudent(studentId, mentorId, true))
                .matches(
                        actual -> actual.getCode().equals(HttpStatus.NOT_FOUND)
                                && actual.getMessage().equals(
                                        MessageFormat.format(NOT_FOUND_STUDENT_WITH_ID, studentId)
                        )
                );
    }

    @Test
    void matchStudentWithMentorNotFound() {
        Mockito.when(studentRepository.existsById(studentId)).thenReturn(true);
        Mockito.when(mentorRepository.existsById(mentorId)).thenReturn(false);
        assertThatExceptionOfType(PabloBullersException.class)
                .isThrownBy(() -> matchingService.matchStudent(studentId, mentorId, true))
                .matches(
                        actual -> actual.getCode().equals(HttpStatus.NOT_FOUND)
                                && actual.getMessage().equals(
                                        MessageFormat.format(NOT_FOUND_MENTOR_WITH_ID, mentorId)
                        )
                );
    }

    @Test
    void matchStudentWithMatchingNotFound() {
        Mockito.when(studentRepository.existsById(studentId)).thenReturn(true);
        Mockito.when(mentorRepository.existsById(mentorId)).thenReturn(true);
        Mockito.when(matchingRepository.findFirstByMentorIdAndStudentId(mentorId, studentId))
                .thenReturn(Optional.empty());
        assertThatExceptionOfType(PabloBullersException.class)
                .isThrownBy(() -> matchingService.matchStudent(studentId, mentorId, true))
                .matches(
                        actual -> actual.getCode().equals(HttpStatus.NOT_FOUND)
                                && actual.getMessage().equals(
                                        MessageFormat.format(
                                                NOT_FOUND_MATCHING_WITH_STUDENT_ID_AND_MENTOR_ID,
                                                studentId,
                                                mentorId
                                        )
                        )
                );
    }

    @Test
    void getMatchingMentorsWithSuccess() {
        String mentorId2 = "aboba3";
        Matching matching1 = MatchingStub.getBaseMatching(studentId, mentorId, true);
        Matching matching2 = MatchingStub.getBaseMatching(studentId, mentorId2, true);

        Mockito.when(studentRepository.existsById(studentId)).thenReturn(true);

        Mockito.when(matchingRepository.findAllByStudentId(studentId)).thenReturn(List.of(matching1, matching2));

        Mockito.when(mentorRepository.existsById(mentorId)).thenReturn(false);

        MentorRequest mentorRequest = MentorStub.getBaseMentorRequest();
        var diplomaTopics = mentorRequest.diplomaTopics()
                .stream()
                .map(diplomaTopicMapper::mapToDiplomaTopic)
                .toList();
        var mentor = mentorMapper.mapToMentor(mentorRequest);
        mentor.setDiplomaTopicIds(List.of("a", "b"));

        Mockito.when(mentorRepository.findById(mentorId2)).thenReturn(Optional.of(mentor));

        Mockito.when(diplomaTopicRepository.findAllById(mentor.getDiplomaTopicIds())).thenReturn(diplomaTopics);

        var mentorResponse = mentorMapper.mapToMentorResponseWithIsApprove(
                mentor,
                diplomaTopics.stream().map(diplomaTopicMapper::mapToDiplomaTopicResponse).toList(),
                matching2.getIsApprove()
        );

        var actual = matchingService.getMatchingMentors(studentId);

        Assertions.assertEquals(List.of(mentorResponse), actual);
    }

    @Test
    void getMatchingMentorsWithStudentNotFound() {
        Mockito.when(studentRepository.existsById(studentId)).thenReturn(false);
        assertThatExceptionOfType(PabloBullersException.class)
                .isThrownBy(() -> matchingService.getMatchingMentors(studentId))
                .matches(
                        actual -> actual.getCode().equals(HttpStatus.NOT_FOUND)
                                && actual.getMessage().equals(
                                        MessageFormat.format(NOT_FOUND_STUDENT_WITH_ID, studentId)
                        )
                );
    }

    @Test
    void getMatchingStudentsWithSuccess() {
        String studentId2 = "aboba3";

        Matching matching1 = MatchingStub.getBaseMatching(studentId, mentorId, true);
        Matching matching2 = MatchingStub.getBaseMatching(studentId2, mentorId, true);

        Mockito.when(mentorRepository.existsById(mentorId)).thenReturn(true);

        Mockito.when(matchingRepository.findAllByMentorId(mentorId)).thenReturn(List.of(matching1, matching2));

        Mockito.when(studentRepository.existsById(studentId)).thenReturn(false);

        StudentRequest studentRequest = StudentStub.getBaseStudentRequest();

        var student = studentMapper.mapToStudent(studentRequest);

        Mockito.when(studentRepository.findById(studentId2)).thenReturn(Optional.of(student));

        var studentResponse = studentMapper.mapToStudentResponseWithIsApprove(
                student,
                matching2.getIsApprove()
        );

        var actual = matchingService.getMatchingStudents(mentorId);

        Assertions.assertEquals(List.of(studentResponse), actual);
    }

    @Test
    void getMatchingStudentsWithMentorNotFound() {
        Mockito.when(mentorRepository.existsById(mentorId)).thenReturn(false);
        assertThatExceptionOfType(PabloBullersException.class)
                .isThrownBy(() -> matchingService.getMatchingStudents(mentorId))
                .matches(
                        actual -> actual.getCode().equals(HttpStatus.NOT_FOUND)
                                && actual.getMessage().equals(
                                        MessageFormat.format(NOT_FOUND_MENTOR_WITH_ID, mentorId)
                        )
                );
    }

}
