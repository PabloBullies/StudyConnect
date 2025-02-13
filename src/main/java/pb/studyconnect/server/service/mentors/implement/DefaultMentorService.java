package pb.studyconnect.server.service.mentors.implement;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pb.studyconnect.server.api.dto.request.MentorRequest;
import pb.studyconnect.server.api.dto.response.MentorResponse;
import pb.studyconnect.server.exception.PabloBullersException;
import pb.studyconnect.server.model.DiplomaTopic;
import pb.studyconnect.server.model.Mentor;
import pb.studyconnect.server.repository.DiplomaTopicRepository;
import pb.studyconnect.server.repository.MentorRepository;
import pb.studyconnect.server.service.mentors.MentorService;
import pb.studyconnect.server.util.mapper.DiplomaTopicMapper;
import pb.studyconnect.server.util.mapper.MentorMapper;

import java.text.MessageFormat;

import static pb.studyconnect.server.util.Messages.NOT_FOUND_MENTOR_WITH_ID;

@Log4j2
@Service
@RequiredArgsConstructor
public class DefaultMentorService implements MentorService {

    private final DiplomaTopicMapper diplomaTopicMapper;

    private final MentorMapper mentorMapper;

    private final MentorRepository mentorRepository;

    private final DiplomaTopicRepository diplomaTopicRepository;

    private static final Marker marker = MarkerManager.getMarker("MENTOR SERVICE");

    @Transactional
    @Override
    public MentorResponse create(MentorRequest mentorRequest) {
        Mentor mentor = mentorMapper.mapToMentor(mentorRequest);
        var diplomaTopics = mentorRequest.diplomaTopics()
                .stream()
                .map(diplomaTopicMapper::mapToDiplomaTopic)
                .toList();

        diplomaTopicRepository.saveAll(diplomaTopics);
        mentor.setDiplomaTopicIds(diplomaTopics.stream().map(DiplomaTopic::getId).toList());
        mentorRepository.save(mentor);
        log.info(marker, "Mentor with name {} was created", mentor.getName());
        return mentorMapper.mapToMentorResponse(
                mentor,
                diplomaTopics.stream().map(diplomaTopicMapper::mapToDiplomaTopicResponse).toList()
        );
    }

    @Transactional
    @Override
    public MentorResponse edit(String mentorId, MentorRequest mentorRequest) {
        var mentor = mentorRepository.findById(mentorId)
                .orElseThrow(
                        () -> new PabloBullersException(
                                HttpStatus.NOT_FOUND,
                                MessageFormat.format(NOT_FOUND_MENTOR_WITH_ID, mentorId)
                        )
                );

        diplomaTopicRepository.deleteAllById(mentor.getDiplomaTopicIds());

        var diplomaTopics = mentorRequest.diplomaTopics()
                .stream()
                .map(diplomaTopicMapper::mapToDiplomaTopic)
                .toList();
        diplomaTopicRepository.saveAll(diplomaTopics);

        mentor.setName(mentorRequest.name());
        mentor.setEmail(mentorRequest.email());
        mentor.setTgNickname(mentorRequest.tgNickname());
        mentor.setScientificInterests(mentorRequest.scientificInterests());
        mentor.setDepartment(mentorRequest.department());
        mentor.setDiplomaTopicIds(diplomaTopics.stream().map(DiplomaTopic::getId).toList());
        mentorRepository.save(mentor);
        log.info(marker, "Mentor with name {} was edited", mentor.getName());
        return mentorMapper.mapToMentorResponse(
                mentor,
                diplomaTopics.stream().map(diplomaTopicMapper::mapToDiplomaTopicResponse).toList()
        );
    }

    @Override
    public MentorResponse get(String mentorId) {
        var mentor = mentorRepository.findById(mentorId)
                .orElseThrow(
                        () -> new PabloBullersException(
                                HttpStatus.NOT_FOUND,
                                MessageFormat.format(NOT_FOUND_MENTOR_WITH_ID, mentorId)
                        )
                );
        var diplomaTopics = diplomaTopicRepository.findAllById(mentor.getDiplomaTopicIds());
        log.info(marker, "Mentor with name {} was received", mentor.getName());
        return mentorMapper.mapToMentorResponse(
                mentor,
                diplomaTopics.stream().map(diplomaTopicMapper::mapToDiplomaTopicResponse).toList()
        );
    }

    @Transactional
    @Override
    public void delete(String mentorId) {
        var mentor = mentorRepository.findById(mentorId)
                .orElseThrow(
                        () -> new PabloBullersException(
                                HttpStatus.NOT_FOUND,
                                MessageFormat.format(NOT_FOUND_MENTOR_WITH_ID, mentorId)
                        )
                );
        if (mentor.getDiplomaTopicIds() != null) {
            diplomaTopicRepository.deleteAllById(mentor.getDiplomaTopicIds());
        }
        mentorRepository.delete(mentor);
        log.info(marker, "Mentor with id {} was deleted", mentorId);
    }


}
