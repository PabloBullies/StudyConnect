package pb.studyconnect.server.service.mentors.implement;

import lombok.RequiredArgsConstructor;
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

import java.util.ArrayList;


@Service
@RequiredArgsConstructor
public class DefaultMentorService implements MentorService {

    private final DiplomaTopicMapper diplomaTopicMapper;

    private final MentorMapper mentorMapper;

    private final MentorRepository mentorRepository;

    private final DiplomaTopicRepository diplomaTopicRepository;

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
                                "Not found mentor with id: '" + mentorId + "'"
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
                                "Not found mentor with id: '" + mentorId + "'"
                        )
                );
        var diplomaTopics = diplomaTopicRepository.findAllById(mentor.getDiplomaTopicIds());
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
                                "Not found mentor with id: '" + mentorId + "'"
                        )
                );
        if (mentor.getDiplomaTopicIds() != null) {
            diplomaTopicRepository.deleteAllById(mentor.getDiplomaTopicIds());
        }
        mentorRepository.delete(mentor);
    }


}
