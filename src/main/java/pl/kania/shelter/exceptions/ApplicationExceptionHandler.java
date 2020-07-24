package pl.kania.shelter.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleNotFound(ResourceNotFoundException exception, WebRequest request) {
        ExceptionDetails details = new ExceptionDetails(new Date(), exception.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(details, HttpStatus.NOT_FOUND);
    }


}
