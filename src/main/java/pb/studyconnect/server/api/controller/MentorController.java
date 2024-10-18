package pb.studyconnect.server.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pb.studyconnect.server.api.dto.request.AddDiplomaTopicRequest;
import pb.studyconnect.server.api.dto.request.AddMentorRequest;
import pb.studyconnect.server.api.dto.response.AddDiplomaTopicResponse;
import pb.studyconnect.server.api.dto.response.AddMentorResponse;
import pb.studyconnect.server.service.mentors.MentorService;

import java.util.List;

import static pb.studyconnect.server.api.path.ApiPaths.DIPLOMA_TOPICS;
import static pb.studyconnect.server.api.path.ApiPaths.MENTORS;
import static pb.studyconnect.server.api.path.ApiPaths.PROFILES;

@RestController
@RequestMapping(PROFILES)
@RequiredArgsConstructor
public class MentorController {

    private final MentorService mentorService;

    @PostMapping(MENTORS)
    public AddMentorResponse create(@RequestBody @Valid AddMentorRequest addMentorRequest) {
        return mentorService.create(addMentorRequest);
    }

    @PatchMapping(MENTORS + "/{id}" + DIPLOMA_TOPICS)
    public List<AddDiplomaTopicResponse> addDiplomaTopics(
            @PathVariable String id,
            @RequestBody @Valid List<AddDiplomaTopicRequest> request
    ) {
        return mentorService.addDiplomaTopics(id, request);
    }
}
