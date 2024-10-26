package pb.studyconnect.server.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public class PabloBullersException extends RuntimeException {

    private final HttpStatus code;

    private final String message;
}
