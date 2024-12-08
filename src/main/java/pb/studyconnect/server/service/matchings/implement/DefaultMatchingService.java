package pb.studyconnect.server.service.matchings.implement;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pb.studyconnect.server.api.dto.response.MentorResponse;
import pb.studyconnect.server.api.dto.response.MentorResponseWithIsApprove;
import pb.studyconnect.server.api.dto.response.StudentResponse;
import pb.studyconnect.server.api.dto.response.StudentResponseWithIsApprove;
import pb.studyconnect.server.exception.PabloBullersException;
import pb.studyconnect.server.model.Matching;
import pb.studyconnect.server.model.Mentor;
import pb.studyconnect.server.repository.DiplomaTopicRepository;
import pb.studyconnect.server.repository.MatchingRepository;
import pb.studyconnect.server.repository.MentorRepository;
import pb.studyconnect.server.repository.StudentRepository;
import pb.studyconnect.server.service.matchings.MatchingService;
import pb.studyconnect.server.util.mapper.DiplomaTopicMapper;
import pb.studyconnect.server.util.mapper.MentorMapper;
import pb.studyconnect.server.util.mapper.StudentMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultMatchingService implements MatchingService {

    private final StudentRepository studentRepository;

    private final MentorRepository mentorRepository;

    private final MatchingRepository matchingRepository;

    private final DiplomaTopicRepository diplomaTopicRepository;

    private final MentorMapper mentorMapper;

    private final DiplomaTopicMapper diplomaTopicMapper;

    private final StudentMapper studentMapper;

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

    @Override
    public List<MentorResponseWithIsApprove> getMatchingMentors(String studentId) {
        var matchings = matchingRepository.findAllByStudentId(studentId);

        List<MentorResponseWithIsApprove> mentorResponses = new ArrayList<>();
        for (Matching matching : matchings) {
            var mentor = mentorRepository.findById(matching.getMentorId());
            if (mentor.isEmpty()) {
                continue;
            }

            var diplomaTopics = diplomaTopicRepository.findAllById(mentor.get().getDiplomaTopicIds());
            var mentorResponse = mentorMapper.mapToMentorResponseWithIsApprove(
                    mentor.get(),
                    diplomaTopics.stream().map(diplomaTopicMapper::mapToDiplomaTopicResponse).toList(),
                    matching.getIsApprove()
            );
            mentorResponses.add(mentorResponse);
        }
        return mentorResponses;
    }

    @Override
    public List<StudentResponseWithIsApprove> getMatchingStudents(String mentorId) {
        var matchings = matchingRepository.findAllByMentorId(mentorId);

        List<StudentResponseWithIsApprove> studentResponses = new ArrayList<>();
        for (Matching matching : matchings) {
            var student = studentRepository.findById(matching.getStudentId());
            if (student.isEmpty()) {
                continue;
            }

            var studentResponse = studentMapper.mapToStudentResponseWithIsApprove(
                    student.get(),
                    matching.getIsApprove()
            );

            studentResponses.add(studentResponse);
        }
        return studentResponses;
    }
}
