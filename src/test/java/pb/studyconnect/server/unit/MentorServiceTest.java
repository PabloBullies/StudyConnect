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

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

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
        mentor.setDiplomaTopicIds(List.of("a", "b"));

        Mockito.when(mentorRepository.findById("aboba")).thenReturn(Optional.of(mentor));

        var actual = mentorService.edit("aboba", mentorRequest);
        var expected = MentorStub.getBaseMentorResponse();
        assertThat(actual).isEqualTo(expected);

        Mockito.verify(diplomaTopicRepository).deleteAllById(List.of("a", "b"));
        Mockito.verify(diplomaTopicRepository).saveAll(diplomaTopics);
        Mockito.verify(mentorRepository).save(mentor);
    }

    @Test
    void mentorEditWithNotFoundErrorTest() {
        MentorRequest mentorRequest = MentorStub.getBaseMentorRequest();
        Mockito.when(mentorRepository.findById("aboba")).thenReturn(Optional.empty());
        assertThatExceptionOfType(PabloBullersException.class)
                .isThrownBy(() -> mentorService.edit("aboba", mentorRequest))
                .matches(
                        actual -> actual.getCode().equals(HttpStatus.NOT_FOUND)
                                && actual.getMessage().equals("Not found mentor with id: 'aboba'")
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
        mentor.setDiplomaTopicIds(List.of("a", "b"));

        Mockito.when(mentorRepository.findById("aboba")).thenReturn(Optional.of(mentor));
        Mockito.when(diplomaTopicRepository.findAllById(mentor.getDiplomaTopicIds())).thenReturn(diplomaTopics);
        var mentorResponse = mentorService.get("aboba");
        Assertions.assertEquals(MentorStub.getBaseMentorResponse(), mentorResponse);
    }

    @Test
    void mentorGetWithNotFoundErrorTest() {
        Mockito.when(mentorRepository.findById("aboba")).thenReturn(Optional.empty());
        assertThatExceptionOfType(PabloBullersException.class)
                .isThrownBy(() -> mentorService.get("aboba"))
                .matches(
                        actual -> actual.getCode().equals(HttpStatus.NOT_FOUND)
                                && actual.getMessage().equals("Not found mentor with id: 'aboba'")
                );
    }

    @Test
    void mentorDeleteWithSuccessTest() {
        MentorRequest mentorRequest = MentorStub.getBaseMentorRequest();
        var mentor = mentorMapper.mapToMentor(mentorRequest);
        mentor.setDiplomaTopicIds(List.of("a", "b"));

        Mockito.when(mentorRepository.findById("aboba")).thenReturn(Optional.of(mentor));
        mentorService.delete("aboba");
        Mockito.verify(diplomaTopicRepository).deleteAllById(mentor.getDiplomaTopicIds());
        Mockito.verify(mentorRepository).delete(mentor);
    }

    @Test
    void studentDeleteWithNotFoundErrorTest() {
        Mockito.when(mentorRepository.findById("aboba")).thenReturn(Optional.empty());
        assertThatExceptionOfType(PabloBullersException.class)
                .isThrownBy(() -> mentorService.delete("aboba"))
                .matches(
                        actual -> actual.getCode().equals(HttpStatus.NOT_FOUND)
                                && actual.getMessage().equals("Not found mentor with id: 'aboba'")
                );
    }
}
