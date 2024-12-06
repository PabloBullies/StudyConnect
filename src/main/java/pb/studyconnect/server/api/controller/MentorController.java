package pb.studyconnect.server.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pb.studyconnect.server.api.dto.request.MentorRequest;
import pb.studyconnect.server.api.dto.response.MentorResponse;
import pb.studyconnect.server.service.mentors.MentorService;

import static pb.studyconnect.server.api.path.ApiPaths.MENTORS;
import static pb.studyconnect.server.api.path.ApiPaths.PROFILES;

@RestController
@RequestMapping(PROFILES)
@RequiredArgsConstructor
public class MentorController {

    private final MentorService mentorService;

    @PostMapping(MENTORS)
    public MentorResponse create(@RequestBody @Valid MentorRequest mentorRequest) {
        return mentorService.create(mentorRequest);
    }

    @PutMapping(MENTORS + "/{id}")
    public MentorResponse edit(@PathVariable String id, @RequestBody @Valid MentorRequest mentorRequest) {
        return mentorService.edit(id, mentorRequest);
    }

    @GetMapping(MENTORS + "/{id}")
    public MentorResponse get(@PathVariable String id) {
        return mentorService.get(id);
    }

    @DeleteMapping(MENTORS + "/{id}")
    public void delete(@PathVariable String id) {
        mentorService.delete(id);
    }
}
