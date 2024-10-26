package pb.studyconnect.server.api.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pb.studyconnect.server.api.dto.response.SuggestMentorResponse;
import pb.studyconnect.server.model.DiplomaTopic;

import java.util.ArrayList;
import java.util.Arrays;

import static pb.studyconnect.server.api.path.ApiPaths.*;

@RestController
@RequestMapping(SUGGESTIONS)
@RequiredArgsConstructor
public class SuggestionsController {
    @GetMapping(MENTORS+"/{id}")
    public SuggestMentorResponse suggest(@PathVariable String id) {
        return new SuggestMentorResponse("0","Александр Власов",
                Arrays.asList("Нефть", "Микроконтроллеры", "OOP"), new ArrayList<DiplomaTopic>(), "КафСИ");

    }
}
