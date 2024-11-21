package pb.studyconnect.server.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pb.studyconnect.server.service.matches.MatchService;

import static pb.studyconnect.server.api.path.ApiPaths.MATCHES;
import static pb.studyconnect.server.api.path.ApiPaths.MENTORS;

@RestController
@RequestMapping(MATCHES)
@RequiredArgsConstructor
public class MatchController {

    private final MatchService matchService;

    @PostMapping("/{studentId}"+ MENTORS + "/{mentorId}")
    public void matchMentor(@PathVariable String studentId, @PathVariable String mentorId) {
        matchService.matchMentor(studentId, mentorId);
    }
}

