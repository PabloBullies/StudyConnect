package pb.studyconnect.server.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pb.studyconnect.server.api.dto.request.AddDiplomaTopicsWithMentorRequest;
import pb.studyconnect.server.api.dto.request.AddMentorRequest;
import pb.studyconnect.server.api.dto.response.AddDiplomaTopicsWithMentorResponse;
import pb.studyconnect.server.api.dto.response.AddMentorResponse;
import pb.studyconnect.server.service.mentors.MentorService;

import static pb.studyconnect.server.api.path.ApiPaths.DIPLOMA_TOPICS;
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

    @PatchMapping(MENTORS + DIPLOMA_TOPICS)
    public AddDiplomaTopicsWithMentorResponse addDiplomaTopics(@RequestBody AddDiplomaTopicsWithMentorRequest request) {
        return mentorService.addDiplomaTopics(request);
    }
}
