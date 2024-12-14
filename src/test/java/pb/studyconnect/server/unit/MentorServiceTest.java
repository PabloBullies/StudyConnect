package pb.studyconnect.server.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pb.studyconnect.server.api.dto.request.MentorRequest;
import pb.studyconnect.server.exception.PabloBullersException;
import pb.studyconnect.server.model.DiplomaTopic;
import pb.studyconnect.server.repository.DiplomaTopicRepository;
import pb.studyconnect.server.repository.MentorRepository;
import pb.studyconnect.server.service.mentors.MentorService;
import pb.studyconnect.server.unit.stub.MentorStub;
import pb.studyconnect.server.util.mapper.DiplomaTopicMapper;
import pb.studyconnect.server.util.mapper.MentorMapper;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static pb.studyconnect.server.util.Messages.NOT_FOUND_MENTOR_WITH_ID;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class MentorServiceTest {

    @Autowired
    private MentorService mentorService;

    @Autowired
    private MentorMapper mentorMapper;

    @Autowired
    private DiplomaTopicMapper diplomaTopicMapper;

    @MockBean
    private MentorRepository mentorRepository;

    @MockBean
    private DiplomaTopicRepository diplomaTopicRepository;

    private final String mentorId = "aboba";

    private final List<String> diplomaTopicIds = List.of("a", "b");

    @Test
    void mentorCreateWithSuccessTest() {
        MentorRequest mentorRequest = MentorStub.getBaseMentorRequest();
        var diplomaTopics = mentorRequest.diplomaTopics()
                .stream()
                .map(diplomaTopicMapper::mapToDiplomaTopic)
                .toList();
        var mentor = mentorMapper.mapToMentor(mentorRequest);
        mentor.setDiplomaTopicIds(diplomaTopics.stream().map(DiplomaTopic::getId).toList());

        var actual = mentorService.create(mentorRequest);
        var expected = MentorStub.getBaseMentorResponse();

        assertThat(actual).isEqualTo(expected);

        Mockito.verify(diplomaTopicRepository).saveAll(diplomaTopics);
        Mockito.verify(mentorRepository).save(mentor);
    }

    @Test
    void mentorEditWithSuccessTest() {
        MentorRequest mentorRequest = MentorStub.getBaseMentorRequest();
        var diplomaTopics = mentorRequest.diplomaTopics()
                .stream()
                .map(diplomaTopicMapper::mapToDiplomaTopic)
                .toList();
        var mentor = mentorMapper.mapToMentor(mentorRequest);
        mentor.setDiplomaTopicIds(diplomaTopicIds);

        Mockito.when(mentorRepository.findById(mentorId)).thenReturn(Optional.of(mentor));

        var actual = mentorService.edit(mentorId, mentorRequest);
        var expected = MentorStub.getBaseMentorResponse();
        assertThat(actual).isEqualTo(expected);

        Mockito.verify(diplomaTopicRepository).deleteAllById(diplomaTopicIds);
        Mockito.verify(diplomaTopicRepository).saveAll(diplomaTopics);
        Mockito.verify(mentorRepository).save(mentor);
    }

    @Test
    void mentorEditWithNotFoundErrorTest() {
        MentorRequest mentorRequest = MentorStub.getBaseMentorRequest();
        Mockito.when(mentorRepository.findById(mentorId)).thenReturn(Optional.empty());
        assertThatExceptionOfType(PabloBullersException.class)
                .isThrownBy(() -> mentorService.edit(mentorId, mentorRequest))
                .matches(
                        actual -> actual.getCode().equals(HttpStatus.NOT_FOUND)
                                && actual.getMessage().equals(
                                MessageFormat.format(NOT_FOUND_MENTOR_WITH_ID, mentorId)
                        )
                );
    }

    @Test
    void mentorGetWithSuccessTest() {
        MentorRequest mentorRequest = MentorStub.getBaseMentorRequest();
        var diplomaTopics = mentorRequest.diplomaTopics()
                .stream()
                .map(diplomaTopicMapper::mapToDiplomaTopic)
                .toList();
        var mentor = mentorMapper.mapToMentor(mentorRequest);
        mentor.setDiplomaTopicIds(diplomaTopicIds);

        Mockito.when(mentorRepository.findById(mentorId)).thenReturn(Optional.of(mentor));
        Mockito.when(diplomaTopicRepository.findAllById(mentor.getDiplomaTopicIds())).thenReturn(diplomaTopics);
        var mentorResponse = mentorService.get(mentorId);
        Assertions.assertEquals(MentorStub.getBaseMentorResponse(), mentorResponse);
    }

    @Test
    void mentorGetWithNotFoundErrorTest() {
        Mockito.when(mentorRepository.findById(mentorId)).thenReturn(Optional.empty());
        assertThatExceptionOfType(PabloBullersException.class)
                .isThrownBy(() -> mentorService.get(mentorId))
                .matches(
                        actual -> actual.getCode().equals(HttpStatus.NOT_FOUND)
                                && actual.getMessage().equals(
                                MessageFormat.format(NOT_FOUND_MENTOR_WITH_ID, mentorId)
                        )
                );
    }

    @Test
    void mentorDeleteWithSuccessTest() {
        MentorRequest mentorRequest = MentorStub.getBaseMentorRequest();
        var mentor = mentorMapper.mapToMentor(mentorRequest);
        mentor.setDiplomaTopicIds(diplomaTopicIds);

        Mockito.when(mentorRepository.findById(mentorId)).thenReturn(Optional.of(mentor));
        mentorService.delete(mentorId);
        Mockito.verify(diplomaTopicRepository).deleteAllById(mentor.getDiplomaTopicIds());
        Mockito.verify(mentorRepository).delete(mentor);
    }

    @Test
    void studentDeleteWithNotFoundErrorTest() {
        Mockito.when(mentorRepository.findById(mentorId)).thenReturn(Optional.empty());
        assertThatExceptionOfType(PabloBullersException.class)
                .isThrownBy(() -> mentorService.delete(mentorId))
                .matches(
                        actual -> actual.getCode().equals(HttpStatus.NOT_FOUND)
                                && actual.getMessage().equals(
                                MessageFormat.format(NOT_FOUND_MENTOR_WITH_ID, mentorId)
                        )
                );
    }
}
