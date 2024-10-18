package pb.studyconnect.server.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class PabloBullersException extends RuntimeException {

    private final int code;

    private final String message;
}
