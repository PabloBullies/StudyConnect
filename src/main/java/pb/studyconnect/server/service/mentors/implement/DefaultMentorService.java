package pb.studyconnect.server.service.mentors.implement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pb.studyconnect.server.api.dto.request.AddMentorRequest;
import pb.studyconnect.server.api.dto.response.AddMentorResponse;
import pb.studyconnect.server.model.Mentor;
import pb.studyconnect.server.repository.MentorRepository;
import pb.studyconnect.server.service.mentors.MentorService;
import pb.studyconnect.server.util.MentorMapper;

@Service
@RequiredArgsConstructor
public class DefaultMentorService implements MentorService {

    private final MentorRepository mentorRepository;

    @Override
    public AddMentorResponse create(AddMentorRequest addMentorRequest) {
        Mentor mentor = MentorMapper.INSTANCE.mapToMentor(addMentorRequest);
        mentorRepository.save(mentor);
        return MentorMapper.INSTANCE.mapToAddMentorResponse(mentor);
    }
}
