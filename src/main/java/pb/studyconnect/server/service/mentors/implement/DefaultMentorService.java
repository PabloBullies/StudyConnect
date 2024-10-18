package pb.studyconnect.server.service.mentors.implement;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pb.studyconnect.server.api.dto.request.DiplomaTopicRequest;
import pb.studyconnect.server.api.dto.request.MentorRequest;
import pb.studyconnect.server.api.dto.response.DiplomaTopicResponse;
import pb.studyconnect.server.api.dto.response.MentorResponse;
import pb.studyconnect.server.exception.PabloBullersException;
import pb.studyconnect.server.model.Mentor;
import pb.studyconnect.server.repository.DiplomaTopicRepository;
import pb.studyconnect.server.repository.MentorRepository;
import pb.studyconnect.server.service.mentors.MentorService;
import pb.studyconnect.server.util.mapper.DiplomaTopicMapper;
import pb.studyconnect.server.util.mapper.MentorMapper;

import java.util.List;

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
        mentor.setDiplomaTopics(diplomaTopics);
        mentorRepository.save(mentor);
        return mentorMapper.mapToMentorResponse(mentor);
    }

    @Transactional
    @Override
    public List<DiplomaTopicResponse> addDiplomaTopics(String mentorId, List<DiplomaTopicRequest> request) {
        var mentor = mentorRepository.findById(mentorId)
                .orElseThrow(
                        () -> new PabloBullersException(
                                HttpStatus.NOT_FOUND.value(),
                                "Not found mentor with id: '" + mentorId + "'"
                        )
                );

        var diplomaTopics = request.stream()
                .map(diplomaTopicMapper::mapToDiplomaTopic)
                .toList();
        diplomaTopicRepository.saveAll(diplomaTopics);

        mentor.setDiplomaTopics(diplomaTopics);
        mentorRepository.save(mentor);
        return diplomaTopics.stream().map(diplomaTopicMapper::mapToDiplomaTopicResponse).toList();
    }

    @Transactional
    @Override
    public MentorResponse edit(String mentorId, MentorRequest mentorRequest) {
        var mentor = mentorRepository.findById(mentorId)
                .orElseThrow(
                        () -> new PabloBullersException(
                                HttpStatus.NOT_FOUND.value(),
                                "Not found mentor with id: '" + mentorId + "'"
                        )
                );

        if (mentor.getDiplomaTopics() != null) {
            diplomaTopicRepository.deleteAll(mentor.getDiplomaTopics());
        }

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
        mentor.setDiplomaTopics(diplomaTopics);
        mentorRepository.save(mentor);
        return mentorMapper.mapToMentorResponse(mentor);
    }

    @Override
    public MentorResponse get(String mentorId) {
        var mentor = mentorRepository.findById(mentorId)
                .orElseThrow(
                        () -> new PabloBullersException(
                                HttpStatus.NOT_FOUND.value(),
                                "Not found mentor with id: '" + mentorId + "'"
                        )
                );
        return mentorMapper.mapToMentorResponse(mentor);
    }

    @Transactional
    @Override
    public void delete(String mentorId) {
        var mentor = mentorRepository.findById(mentorId)
                .orElseThrow(
                        () -> new PabloBullersException(
                                HttpStatus.NOT_FOUND.value(),
                                "Not found mentor with id: '" + mentorId + "'"
                        )
                );
        if (mentor.getDiplomaTopics() != null) {
            diplomaTopicRepository.deleteAll(mentor.getDiplomaTopics());
        }
        mentorRepository.delete(mentor);
    }


}
