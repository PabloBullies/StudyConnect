package pb.studyconnect.server.api.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pb.studyconnect.server.api.dto.response.MentorResponse;
import pb.studyconnect.server.service.suggestions.SuggestionService;

import static pb.studyconnect.server.api.path.ApiPaths.*;

@RestController
@RequestMapping(SUGGESTIONS)
@RequiredArgsConstructor
public class SuggestionController {

    private final SuggestionService suggestionService;

    @GetMapping("/{studentId}" + MENTORS)
    public MentorResponse suggestMentor(@PathVariable String studentId, @RequestParam Integer skip) {
        return suggestionService.suggestMentor(studentId, skip);
    }
}
