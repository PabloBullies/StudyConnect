package pb.studyconnect.server.api.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pb.studyconnect.server.api.dto.response.MentorResponse;
import pb.studyconnect.server.api.dto.response.SuggestMentorResponse;
import pb.studyconnect.server.model.DiplomaTopic;
import pb.studyconnect.server.service.suggestions.mentors.SuggestMentorService;

import java.util.ArrayList;
import java.util.Arrays;

import static pb.studyconnect.server.api.path.ApiPaths.*;

@RestController
@RequestMapping(SUGGESTIONS)
@RequiredArgsConstructor
public class SuggestionController {

    private final SuggestMentorService suggestMentorService;

    @GetMapping(MENTORS+"/{studentId}")
    public MentorResponse suggestMentor(@PathVariable String studentId, @RequestParam Integer offset) {
        return suggestMentorService.suggest(studentId, offset);
    }
}
