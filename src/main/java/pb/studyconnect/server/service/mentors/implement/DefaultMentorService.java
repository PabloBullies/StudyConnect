package pb.studyconnect.server.service.mentors.implement;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pb.studyconnect.server.api.dto.request.AddDiplomaTopicRequest;
import pb.studyconnect.server.api.dto.request.AddMentorRequest;
import pb.studyconnect.server.api.dto.response.AddDiplomaTopicResponse;
import pb.studyconnect.server.api.dto.response.AddMentorResponse;
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

    @Override
    public AddMentorResponse create(AddMentorRequest addMentorRequest) {
        Mentor mentor = mentorMapper.mapToMentor(addMentorRequest);
        mentorRepository.save(mentor);
        return mentorMapper.mapToAddMentorResponse(mentor);
    }

    @Transactional
    @Override
    public List<AddDiplomaTopicResponse> addDiplomaTopics(String mentorId, List<AddDiplomaTopicRequest> request) {
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

        if (mentor.getDiplomaTopics() == null) {
            mentor.setDiplomaTopics(diplomaTopics);
        } else {
            mentor.getDiplomaTopics().addAll(diplomaTopics);
        }
        mentorRepository.save(mentor);
        return diplomaTopics.stream().map(diplomaTopicMapper::mapToDiplomaTopicResponse).toList();
    }
}
