package pb.studyconnect.server.service.matches.implement;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pb.studyconnect.server.exception.PabloBullersException;
import pb.studyconnect.server.model.Match;
import pb.studyconnect.server.repository.MatchRepository;
import pb.studyconnect.server.repository.MentorRepository;
import pb.studyconnect.server.repository.StudentRepository;
import pb.studyconnect.server.service.matches.MatchService;

@Service
@RequiredArgsConstructor
public class DefaultMatchService implements MatchService {

    private final StudentRepository studentRepository;

    private final MentorRepository mentorRepository;

    private final MatchRepository matchRepository;

    @Override
    public void matchMentor(String studentId, String mentorId) {
        studentRepository.findById(studentId).orElseThrow(
                () -> new PabloBullersException(
                        HttpStatus.NOT_FOUND,
                        "Not found student with id: '" + studentId + "'"
                )
        );

        mentorRepository.findById(mentorId).orElseThrow(
                () -> new PabloBullersException(
                        HttpStatus.NOT_FOUND,
                        "Not found student with id: '" + studentId + "'"
                )
        );

        var matchDb = matchRepository.findFirstByMentorIdAndStudentId(mentorId, studentId);
        if (matchDb.isPresent()) {
            return;
        }

        Match match = new Match();
        match.setMentorId(mentorId);
        match.setStudentId(studentId);
        match.setIsApprove(false);

        matchRepository.save(match);
    }
}
