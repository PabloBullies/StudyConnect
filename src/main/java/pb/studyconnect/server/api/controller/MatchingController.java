package pb.studyconnect.server.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pb.studyconnect.server.api.dto.response.MentorResponse;
import pb.studyconnect.server.api.dto.response.StudentResponse;
import pb.studyconnect.server.service.matchings.MatchingService;

import java.util.List;

import static pb.studyconnect.server.api.path.ApiPaths.MATCHES;
import static pb.studyconnect.server.api.path.ApiPaths.MENTORS;
import static pb.studyconnect.server.api.path.ApiPaths.STUDENTS;

@RestController
@RequestMapping(MATCHES)
@RequiredArgsConstructor
public class MatchingController {

    private final MatchingService matchingService;

    @PostMapping("/{studentId}"+ MENTORS + "/{mentorId}")
    public void matchMentor(@PathVariable String studentId, @PathVariable String mentorId) {
        matchingService.matchMentor(studentId, mentorId);
    }

    @PostMapping("/{mentorId}" + STUDENTS + "/{studentId}" + "/")
    public void matchStudent(
            @PathVariable String studentId,
            @PathVariable String mentorId,
            @RequestParam Boolean isApprove
    ) {
        matchingService.matchStudent(studentId, mentorId, isApprove);
    }

    @GetMapping("/{studentId}" + MENTORS)
    public List<MentorResponse> getMatchingMentors(@PathVariable String studentId) {
        return null;
    }

    @GetMapping("/{mentorId}" + STUDENTS)
    public List<StudentResponse> getMatchingStudents(@PathVariable String mentorId) {
        return null;
    }
}

