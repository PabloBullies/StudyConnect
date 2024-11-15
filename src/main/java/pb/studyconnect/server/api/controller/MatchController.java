package pb.studyconnect.server.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pb.studyconnect.server.api.dto.response.MentorResponse;
import pb.studyconnect.server.service.matches.mentors.MatchMentorService;
import pb.studyconnect.server.service.suggestions.mentors.SuggestMentorService;

import static pb.studyconnect.server.api.path.ApiPaths.MATCHES;
import static pb.studyconnect.server.api.path.ApiPaths.MENTORS;
import static pb.studyconnect.server.api.path.ApiPaths.SUGGESTIONS;

@RestController
@RequestMapping(MATCHES)
@RequiredArgsConstructor
public class MatchController {

    private final MatchMentorService matchMentorService;

    @PostMapping(MENTORS+"/{mentorId}/{studentId}")
    public void matchMentor(@PathVariable String studentId, @PathVariable String mentorId) {
        matchMentorService.match(studentId, mentorId);
    }
}

