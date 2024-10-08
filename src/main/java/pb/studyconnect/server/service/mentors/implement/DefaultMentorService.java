package pb.studyconnect.server.service.mentors.implement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pb.studyconnect.server.api.dto.request.AddDiplomaTopicsWithMentorRequest;
import pb.studyconnect.server.api.dto.request.AddMentorRequest;
import pb.studyconnect.server.api.dto.response.AddDiplomaTopicsWithMentorResponse;
import pb.studyconnect.server.api.dto.response.AddMentorResponse;
import pb.studyconnect.server.model.Mentor;
import pb.studyconnect.server.repository.DiplomaTopicRepository;
import pb.studyconnect.server.repository.MentorRepository;
import pb.studyconnect.server.service.mentors.MentorService;
import pb.studyconnect.server.util.DiplomaTopicMapper;
import pb.studyconnect.server.util.MentorMapper;

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
    public AddDiplomaTopicsWithMentorResponse addDiplomaTopics(AddDiplomaTopicsWithMentorRequest request) {
        //потом будет норм обработка ошибок(дисклеймер чтоб Коля не докопался)
        var mentor = mentorRepository.findById(request.mentorId()).orElseThrow(RuntimeException::new);

        var diplomaTopics = request.diplomaTopics()
                .stream()
                .map(diplomaTopicMapper::mapToDiplomaTopic)
                .toList();
        diplomaTopicRepository.saveAll(diplomaTopics);

        mentor.getDiplomaTopics().addAll(diplomaTopics);
        mentorRepository.save(mentor);
        return new AddDiplomaTopicsWithMentorResponse(
                mentor.getId(),
                diplomaTopics.stream().map(diplomaTopicMapper::mapToDiplomaTopicResponse).toList()
        );
    }
}
