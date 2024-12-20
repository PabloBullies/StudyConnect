package pb.studyconnect.server.api.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pb.studyconnect.server.exception.PabloBullersException;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handle(MethodArgumentNotValidException ex) {
        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> errors.add(error.getDefaultMessage()));
        log.error(errors.toString(), ex);
        return ResponseEntity.badRequest().body(errors.toString());
    }

    @ExceptionHandler(PabloBullersException.class)
    public ResponseEntity<String> handle(PabloBullersException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(ex.getCode()).body(ex.getMessage());
    }
}
