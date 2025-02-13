package pb.studyconnect.server.service.matchings.implement;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
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

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static pb.studyconnect.server.util.Messages.NOT_FOUND_MATCHING_WITH_STUDENT_ID_AND_MENTOR_ID;
import static pb.studyconnect.server.util.Messages.NOT_FOUND_MENTOR_WITH_ID;
import static pb.studyconnect.server.util.Messages.NOT_FOUND_STUDENT_WITH_ID;

@Log4j2
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

    private static final Marker marker = MarkerManager.getMarker("MATCHING SERVICE");

    @Override
    public void matchMentor(String studentId, String mentorId) {
        if (!studentRepository.existsById(studentId)) {
            throw new PabloBullersException(
                    HttpStatus.NOT_FOUND,
                    MessageFormat.format(NOT_FOUND_STUDENT_WITH_ID, studentId)
            );
        }

        if (!mentorRepository.existsById(mentorId)) {
            throw new PabloBullersException(
                    HttpStatus.NOT_FOUND,
                    MessageFormat.format(NOT_FOUND_MENTOR_WITH_ID, mentorId)

            );
        }

        var matchingDb = matchingRepository.findFirstByMentorIdAndStudentId(mentorId, studentId);
        if (matchingDb.isPresent()) {
            return;
        }

        Matching matching = new Matching();
        matching.setMentorId(mentorId);
        matching.setStudentId(studentId);
        matching.setIsApprove(false);

        matchingRepository.save(matching);
        log.info(marker, "Student with id {} made match to a mentor with id {}", studentId, mentorId);
    }

    @Override
    public void matchStudent(String studentId, String mentorId, Boolean isApprove) {
        if (!studentRepository.existsById(studentId)) {
            throw new PabloBullersException(
                    HttpStatus.NOT_FOUND,
                    MessageFormat.format(NOT_FOUND_STUDENT_WITH_ID, studentId)
            );
        }

        if (!mentorRepository.existsById(mentorId)) {
            throw new PabloBullersException(
                    HttpStatus.NOT_FOUND,
                    MessageFormat.format(NOT_FOUND_MENTOR_WITH_ID, mentorId)
            );
        }

        var matchingDb = matchingRepository.findFirstByMentorIdAndStudentId(mentorId, studentId).orElseThrow(
                () -> new PabloBullersException(
                        HttpStatus.NOT_FOUND,
                        MessageFormat.format(NOT_FOUND_MATCHING_WITH_STUDENT_ID_AND_MENTOR_ID, studentId, mentorId)
                )
        );

        matchingDb.setIsApprove(isApprove);
        matchingRepository.save(matchingDb);
        log.info(marker, "Mentor with id {} made {} approve to a student with id {}", mentorId, isApprove,  studentId);
    }

    @Override
    public List<MentorResponseWithIsApprove> getMatchingMentors(String studentId) {
        if (!studentRepository.existsById(studentId)) {
            throw new PabloBullersException(
                    HttpStatus.NOT_FOUND,
                    MessageFormat.format(NOT_FOUND_STUDENT_WITH_ID, studentId)
            );
        }

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
        log.info(marker, "All matching mentors have been received for student with id {}", studentId);
        return mentorResponses;
    }

    @Override
    public List<StudentResponseWithIsApprove> getMatchingStudents(String mentorId) {
        if (!mentorRepository.existsById(mentorId)) {
            throw new PabloBullersException(
                    HttpStatus.NOT_FOUND,
                    MessageFormat.format(NOT_FOUND_MENTOR_WITH_ID, mentorId)
            );
        }

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
        log.info(marker, "All matching students have been received for mentor with id {}", mentorId);
        return studentResponses;
    }
}
