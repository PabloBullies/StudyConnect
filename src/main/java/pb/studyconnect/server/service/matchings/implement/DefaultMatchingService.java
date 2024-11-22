package pb.studyconnect.server.service.matchings.implement;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pb.studyconnect.server.exception.PabloBullersException;
import pb.studyconnect.server.model.Matching;
import pb.studyconnect.server.repository.MatchingRepository;
import pb.studyconnect.server.repository.MentorRepository;
import pb.studyconnect.server.repository.StudentRepository;
import pb.studyconnect.server.service.matchings.MatchingService;

@Service
@RequiredArgsConstructor
public class DefaultMatchingService implements MatchingService {

    private final StudentRepository studentRepository;

    private final MentorRepository mentorRepository;

    private final MatchingRepository matchingRepository;

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

        var matchingDb = matchingRepository.findFirstByMentorIdAndStudentId(mentorId, studentId);
        if (matchingDb.isPresent()) {
            return;
        }

        Matching matching = new Matching();
        matching.setMentorId(mentorId);
        matching.setStudentId(studentId);
        matching.setIsApprove(false);

        matchingRepository.save(matching);
    }

    @Override
    public void matchStudent(String studentId, String mentorId, Boolean isApprove) {
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

        var matchingDb = matchingRepository.findFirstByMentorIdAndStudentId(mentorId, studentId).orElseThrow(
                () -> new PabloBullersException(
                        HttpStatus.NOT_FOUND,
                        "Not found matching with student id '" + studentId + "' and mentor id '" + mentorId + "'"
                )
        );

        matchingDb.setIsApprove(isApprove);
        matchingRepository.save(matchingDb);
    }
}
