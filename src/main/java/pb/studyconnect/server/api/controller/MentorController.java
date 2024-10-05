package pb.studyconnect.server.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pb.studyconnect.server.api.dto.request.AddMentorRequest;
import pb.studyconnect.server.api.dto.response.AddMentorResponse;
import pb.studyconnect.server.service.mentors.MentorService;

import static pb.studyconnect.server.api.path.ApiPaths.MENTORS;
import static pb.studyconnect.server.api.path.ApiPaths.PROFILES;

@RestController
@RequestMapping(PROFILES)
@RequiredArgsConstructor
public class MentorController {

    private final MentorService mentorService;

    @PostMapping(MENTORS)
    public AddMentorResponse create(@RequestBody AddMentorRequest addMentorRequest) {
        return mentorService.create(addMentorRequest);
    }
}
