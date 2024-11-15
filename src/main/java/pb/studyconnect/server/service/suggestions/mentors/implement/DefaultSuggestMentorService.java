package pb.studyconnect.server.service.suggestions.mentors.implement;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pb.studyconnect.server.api.dto.response.MentorResponse;
import pb.studyconnect.server.exception.PabloBullersException;
import pb.studyconnect.server.model.DiplomaTopic;
import pb.studyconnect.server.model.Mentor;
import pb.studyconnect.server.repository.MentorRepository;
import pb.studyconnect.server.repository.StudentRepository;
import pb.studyconnect.server.service.suggestions.mentors.SuggestMentorService;
import pb.studyconnect.server.util.mapper.MentorMapper;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultSuggestMentorService implements SuggestMentorService {

    private final StudentRepository studentRepository;

    private final MentorRepository mentorRepository;

    private final MentorMapper mentorMapper;

    @Override
    public MentorResponse suggest(String studentId, Integer offset) {
        var student = studentRepository.findById(studentId)
                .orElseThrow(
                        () -> new PabloBullersException(
                                HttpStatus.NOT_FOUND,
                                "Not found student with id: '" + studentId + "'"
                        )
                );

        List<Mentor> mentors = mentorRepository.findAll();
        Mentor mentorForResponse = null;
        for(Mentor mentor : mentors) {
            for(DiplomaTopic diplomaTopic : mentor.getDiplomaTopics()) {
                if (!Collections.disjoint(diplomaTopic.getNeededSkills(), student.getSkills())) {
                    offset -= 1;
                    break;
                }
            }
            if (offset == -1) {
                mentorForResponse = mentor;
                break;
            }
        }

        return mentorMapper.mapToMentorResponse(mentorForResponse);
    }
}
